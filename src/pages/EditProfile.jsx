import React from 'react';
import PageHeader from '../components/PageHeader';
import useProfileHook from '../hooks/useProfileHook';
import Skeleton, { SkeletonTheme } from 'react-loading-skeleton';
import 'react-loading-skeleton/dist/skeleton.css';

const EditProfile = () => {
    const {
        profileData,
        isBusy,
        handleInputChange,
        handleImageChange,
        handleCancel,
        handleSave,
    } = useProfileHook();
    
    return (
        <SkeletonTheme baseColor="#d1d1d1" highlightColor="#aaaaaa">
            <div className="flex-1 min-h-screen p-4 py-10 overflow-y-auto bg-gray-100 lg:ml-64">
                <PageHeader
                    title="Ubah Pengaturan Akun"
                    subtitle="Ini adalah halaman berisi ubah foto profil dan informasi pribadi admin Agrowisata Tepas Papandayan."
                />

                <form onSubmit={handleSave}>
                    <div className="p-4 mb-6 bg-white rounded-lg md:p-6">
                        <h2 className="mb-4 font-semibold text-center">Ubah Foto Profil</h2>
                        <div className="flex flex-col items-center gap-4">
                            <div className="w-32 h-32 overflow-hidden rounded-full">
                                {isBusy() ? (
                                    <Skeleton circle={true} height="100%" width="100%" />
                                ) : (
                                    <img
                                        src={profileData.picture || 'https://via.placeholder.com/150'}
                                        alt="Profile"
                                        className="object-cover w-full h-full"
                                    />
                                )}
                            </div>
                            <label className="px-4 py-2 text-white bg-teal-600 rounded cursor-pointer">
                                Pilih Foto
                                <input
                                    type="file"
                                    className="hidden"
                                    accept="image/*"
                                    onChange={handleImageChange}
                                />
                            </label>
                        </div>
                    </div>

                    <div className="p-4 mb-6 bg-white rounded-lg md:p-6">
                        <h3 className="mb-4 font-semibold text-center">Ubah Informasi Pribadi</h3>
                        <div className="space-y-4">
                            {[
                                { label: 'Username', name: 'username', type: 'text', autocomplete: 'username' },
                                { label: 'No. Handphone', name: 'phone_number', type: 'tel' },
                                { label: 'Email', name: 'email', type: 'email', autocomplete: 'email' },
                                { label: 'Password Baru', name: 'password', type: 'password', autocomplete: 'current-password' },
                                { label: 'Alamat', name: 'address', type: 'text' },
                                { label: 'Bio', name: 'bio', type: 'text' },
                            ].map((field, index) => (
                                <div
                                    key={index}
                                    className="flex flex-col gap-2 md:flex-row md:justify-between md:items-center"
                                >
                                    <label htmlFor={field.name} className="text-gray-600">
                                        {field.label}
                                    </label>
                                    {isBusy() ? (
                                        <Skeleton height={40} />
                                    ) : (
                                        <input
                                            type={field.type}
                                            id={field.name}
                                            name={field.name}
                                            className="bg-gray-100 px-4 py-2 rounded w-full md:w-2/3 lg:w-[600px] text-start md:text-end"
                                            value={profileData[field.name] || ''}
                                            onChange={handleInputChange}
                                            autoComplete={field.autocomplete || 'off'}
                                        />
                                    )}
                                </div>
                            ))}
                        </div>
                    </div>

                    <div className="flex justify-end gap-3 mt-5">
                        <button
                            type="button"
                            onClick={handleCancel}
                            className="bg-[#BE1E2D] text-white px-6 py-2 rounded w-full md:w-auto hover:bg-[#9C1D28]"
                        >
                            Batal
                        </button>
                        <button
                            type="submit"
                            disabled={!profileData.password || isBusy()}
                            className={`w-full px-6 py-2 rounded md:w-auto ${profileData.password && !isBusy()
                                ? 'bg-teal-600 text-white hover:bg-teal-700'
                                : 'bg-gray-300 text-gray-500 cursor-not-allowed'
                            }`}
                        >
                            {isBusy() ? 'Memproses...' : 'Simpan'}
                        </button>
                    </div>
                </form>
            </div>
        </SkeletonTheme>
    );
};

export default EditProfile;
