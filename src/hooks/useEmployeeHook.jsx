import { useState, useEffect, useCallback, useRef } from "react";
import { useNavigate } from "react-router-dom";
import Modal from "react-modal"; 
import {
  getEmployees,
  deleteEmployee,
  getEmployeeById,
  updateEmployee,
  createEmployee,
  filterEmployees,
} from "../services/api/apiEmployee";
import { toast } from "react-toastify";
Modal.setAppElement("#root");

const useEmployeeHook = () => {
  const [isButtonDisabled, setIsButtonDisabled] = useState(true);
  const [employees, setEmployees] = useState([]);
  const [page, setPage] = useState(1);
  const [query, setQuery] = useState("");
  const [filterQuery, setFilterQuery] = useState("");
  const [total, setTotal] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [image, setImage] = useState(null);
  const inputFileRef = useRef(null);
  const navigate = useNavigate();
  const [employeeData, setEmployeeData] = useState({
    nip: "",
    name: "",
    position: "",
    phone_number: "",
    picture: null,
  });

  const [loading, setLoading] = useState(false);
  const [uploading, setUploading] = useState(false);
  const [updating, setUpdating] = useState(false);
  const isBusy = () => loading || uploading || updating;

  const [showModal, setShowModal] = useState(false); 
  const [selectedEmployeeId, setSelectedEmployeeId] = useState(null); 


  const setLoadingState = (type, value) => {
    if (type === "loading") {
      setLoading(value);
    } else if (type === "uploading") {
      setUploading(value);
    } else if (type === "updating") {
      setUpdating(value);
    }
  };

  const convertToFormData = (data) => {
    const formData = new FormData();
    formData.append("nip", data.nip);
    formData.append("name", data.name);
    formData.append("position", data.position);
    formData.append("phone_number", data.phone_number);
    formData.append("picture", data.picture);

    return formData;
  };

  const handleGetEmployees = async () => {
    setLoadingState("loading", true);
    try {
      const response = await getEmployees(page, query);
      setEmployees(response.data.rows);
      setTotal(response.data.total);
      setTotalPages(response.data.totalPages);
    } catch (error) {
      const errorMessage = "Gagal mengambil data pegawai!";
      toast.error(errorMessage);
    } finally {
      setLoadingState("loading", false);
    }
  };

  const handleDeleteEmployee = async (id) => {

      setLoadingState("loading", true);
      try {
        await deleteEmployee(id);
        toast.success("Pegawai berhasil dihapus!");
        handleGetEmployees();
      } catch (error) {
        const errorMessage = "Gagal menghapus pegawai. Silakan coba lagi!";
        toast.error(errorMessage);
      } finally {
        setLoadingState("loading", false);
        setShowModal(false); 
      }
    
  };

  const handleGetEmployeeById = useCallback(async (id) => {
    setLoadingState("loading", true);
    try {
      const response = await getEmployeeById(id);
      setEmployeeData(response.data);
    } catch (error) {
      const errorMessage = "Gagal mengambil data pegawai!";
      toast.error(errorMessage);
    } finally {
      setLoadingState("loading", false);
    }
  }, []);

  const handleUpdateEmployee = async (id) => {
    setLoadingState("updating", true);
    try {
      const formData = convertToFormData(employeeData);
      await updateEmployee(id, formData);
      toast.success("Pegawai berhasil diperbarui!");
      handleGetEmployees();
      navigate(-1);
    } catch (error) {
      const errorMessage = "Gagal memperbarui pegawai!";
      toast.error(errorMessage);

    } finally {
      setLoadingState("updating", false);
    }
  };

  const handleCreateEmployee = async () => {
    setLoadingState("updating", true);
    try {
      const formData = convertToFormData(employeeData);
      await createEmployee(formData);
      toast.success("Pegawai berhasil ditambahkan!");
      setEmployeeData({
        nip: "",
        name: "",
        position: "",
        phone_number: "",
        picture: null,
      });
      setImage(null);
      if (inputFileRef.current) {
        inputFileRef.current.value = "";
      }
      handleGetEmployees();
    } catch (error) {
      const errorMessage = "Gagal menambahkan pegawai!";
      toast.error(errorMessage);
    } finally {
      setLoadingState("updating", false);
    }
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      setEmployeeData((prevData) => ({
        ...prevData,
        picture: file,
      }));
      setImage(URL.createObjectURL(file));
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setEmployeeData((prevData) => ({ ...prevData, [name]: value }));
  
    const { nip, position, phone_number } = { ...employeeData, [name]: value };
  
    const isButtonDisabled =
      nip.length !== 18 ||               
      position.trim().length === 0 ||  
      phone_number.length < 10 ||     
      phone_number.length > 15;        
  
    setIsButtonDisabled(isButtonDisabled);
  };

      const handleFilterEmployees = async () => {
        setLoadingState("loading", true);
        try {
          const response = await filterEmployees(page, filterQuery);
          setEmployees(response.data.rows);
          setTotal(response.data.total);
          setTotalPages(response.data.totalPages);
        } catch (error) {
          const errorMessage = "Gagal mengambil data pegawai!";
          toast.error(errorMessage);
        } finally {
          setLoadingState("loading", false);
        }
      };
    
  

  const handleCancel = () => {
    navigate(-1);
  };

  const handleCreate = (e) => {
    e.preventDefault();
    handleCreateEmployee(employeeData);
  };

  const confirmDelete = (id) => {
    setSelectedEmployeeId(id);
    setShowModal(true);
  };

  const cancelDelete = () => {
    setShowModal(false);
    setSelectedEmployeeId(null);
  };

  const proceedDelete = () => {
    handleDeleteEmployee(selectedEmployeeId);
    setShowModal(false);
    setSelectedEmployeeId(null);
  };

  useEffect(() => {
    handleGetEmployees();
  }, [page, query]);

  return {
    employees,
    page,
    setPage,
    total,
    totalPages,
    setQuery,
    handleGetEmployees,
    handleDeleteEmployee,
    employeeData,
    setEmployeeData,
    handleGetEmployeeById,
    handleUpdateEmployee,
    handleCreateEmployee,
    image,
    handleImageChange,
    inputFileRef,
    isBusy,
    handleInputChange,
    handleCancel,
    handleCreate,
    showModal,
    setShowModal,
    selectedEmployeeId,
    setSelectedEmployeeId,
    confirmDelete,
    cancelDelete,
    proceedDelete,
    isButtonDisabled,
    filterQuery,
    setFilterQuery,
    handleFilterEmployees,
  };
};

export default useEmployeeHook;