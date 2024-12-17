import { useState, useEffect } from 'react';
import { getProfile, updateProfile, addProfilePicture } from '../services/api/apiProfile';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify'; 

const useProfileHook = () => {
    const navigate = useNavigate();
    const [profileData, setProfileData] = useState({
        email: '',
        username: '',
        address: '',
        phone_number: '',
        password: '',
        bio: '',
        picture: null,
    });
    const [loading, setLoading] = useState(false);
    const [uploading, setUploading] = useState(false);
    const [updating, setUpdating] = useState(false);
    const isBusy = () => loading || uploading || updating;

    const setLoadingState = (type, value) => {
        if (type === 'loading') {
            setLoading(value);
        } else if (type === 'uploading') {
            setUploading(value);
        } else if (type === 'updating') { 
            setUpdating(value);
        }
    };

    const handleGetProfile = async () => {
        setLoadingState('loading', true);
        try {
            const response = await getProfile();
            setProfileData(response.data);
        } catch (error) {
            const errorMessage =  'Gagal memuat data profil!';
            toast.error(errorMessage);
        } finally {
            setLoadingState('loading', false);
        }
    };

    const handleUpdateProfile = async () => {
        setLoadingState('updating', true);
        try {
            const { picture, ...dataWithoutPicture } = profileData;
            await updateProfile(dataWithoutPicture);
            toast.success('Perubahan berhasil disimpan!');
            navigate(-1);
            handleGetProfile();
        } catch (error) {
            const errorMessage =  'Terjadi kesalahan saat menyimpan perubahan!';
            toast.error(errorMessage);
        } finally {
            setLoadingState('updating', false);
        }
    };

    const handleAddProfilePicture = async (file) => {
        if (!file) {
            toast.warning('Harap pilih file gambar!');
            return;
        }

        const formData = new FormData();
        formData.append('picture', file);

        setLoadingState('uploading', true);
        try {
            await addProfilePicture(formData);
            toast.success('Foto profil berhasil diunggah!');
            await handleGetProfile();
        } catch (error) {
            const errorMessage =  'Terjadi kesalahan saat mengunggah foto profil!';
            toast.error(errorMessage);
        } finally {
            setLoadingState('uploading', false);
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setProfileData((prev) => ({ ...prev, [name]: value }));
    };

    const handleImageChange = (e) => {
        const file = e.target.files[0];
        if (file) {
            handleAddProfilePicture(file);
        }
    };

    const handleSave = async (e) => {
        e.preventDefault();
        await handleUpdateProfile(); 
    };
    

    const handleCancel = () => {
        navigate(-1);
    };

    useEffect(() => {
        handleGetProfile();
    }, []);

    return {
        profileData,
        loading,
        uploading,
        updating,
        isBusy,
        handleInputChange,
        handleImageChange,
        handleUpdateProfile,
        setProfileData,
        handleGetProfile,
        handleSave,
        handleCancel,
    };
};

export default useProfileHook;
