import { useState, useEffect, useCallback, useRef } from "react";
import { useNavigate } from "react-router-dom";
import Modal from "react-modal";
import {
  getInventorys,
  deleteInventory,
  getInventoryById,
  updateInventory,
  createInventory,
  filterInventorys,
} from "../services/api/apiInventory";
import { toast } from "react-toastify";
Modal.setAppElement("#root");

const useInventoryHook = () => {
  const [isButtonDisabled, setIsButtonDisabled] = useState(true);
  const [inventorys, setInventorys] = useState([]);
  const [page, setPage] = useState(1);
  const [query, setQuery] = useState("");
  const [filterQuery, setFilterQuery] = useState("");
  const [total, setTotal] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [image, setImage] = useState(null);
  const inputFileRef = useRef(null);
  const navigate = useNavigate();
  const [inventoryData, setInventoryData] = useState({
    code: "",
    name: "",
    stock_quantity: "",
    price: "",
    picture: null,
  });

  const [loading, setLoading] = useState(false);
  const [uploading, setUploading] = useState(false);
  const [updating, setUpdating] = useState(false);
  const isBusy = () => loading || uploading || updating;

  const [showModal, setShowModal] = useState(false);
  const [selectedInventoryId, setSelectedInventoryId] = useState(null);


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
    formData.append("code", data.code);
    formData.append("name", data.name);
    formData.append("stock_quantity", data.stock_quantity);
    formData.append("price", data.price);
    formData.append("picture", data.picture);

    return formData;
  };

  const handleGetInventorys = async () => {
    setLoadingState("loading", true);
    try {
      const response = await getInventorys(page, query);
      setInventorys(response.data.rows);
      setTotal(response.data.total);
      setTotalPages(response.data.totalPages);
    } catch (error) {
      const errorMessage = "Gagal mengambil data inventaris!";
      toast.error(errorMessage);
    } finally {
      setLoadingState("loading", false);
    }
  };

  const handleDeleteInventory = async (id) => {

    setLoadingState("loading", true);
    try {
      await deleteInventory(id);
      toast.success("Inventaris berhasil dihapus!");
      handleGetInventorys();
    } catch (error) {
      const errorMessage = "Gagal menghapus inventaris. Silakan coba lagi!";
      toast.error(errorMessage);
    } finally {
      setLoadingState("loading", false);
      setShowModal(false);
    }

  };

  const handleGetInventoryById = useCallback(async (id) => {
    setLoadingState("loading", true);
    try {
      const response = await getInventoryById(id);
      setInventoryData(response.data);
    } catch (error) {
      const errorMessage = "Gagal mengambil data inventaris!";
      toast.error(errorMessage);
    } finally {
      setLoadingState("loading", false);
    }
  }, []);

  const handleUpdateInventory = async (id) => {
    setLoadingState("updating", true);
    try {
      const formData = convertToFormData(inventoryData);
      await updateInventory(id, formData);
      toast.success("Inventaris berhasil diperbarui!");
      handleGetInventorys();
      navigate(-1);
    } catch (error) {
      const errorMessage = "Gagal memperbarui inventaris!";
      toast.error(errorMessage);
    } finally {
      setLoadingState("updating", false);
    }
  };

  const handleCreateInventory = async () => {
    setLoadingState("updating", true);
    try {
      const formData = convertToFormData(inventoryData);
      await createInventory(formData);
      toast.success("Inventaris berhasil ditambahkan!");
      setInventoryData({
        code: "",
        name: "",
        stock_quantity: "",
        price: "",
        picture: null,
      });
      setImage(null);
      if (inputFileRef.current) {
        inputFileRef.current.value = "";
      }
      handleGetInventorys();
    } catch (error) {
      const errorMessage = "Gagal menambahkan inventaris!";
      toast.error(errorMessage);

    } finally {
      setLoadingState("updating", false);
    }
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      setInventoryData((prevData) => ({
        ...prevData,
        picture: file,
      }));
      setImage(URL.createObjectURL(file));
    }
  };

  const handleFilterInventorys = async () => {
    setLoadingState("loading", true);
    try {
      const response = await filterInventorys(page, filterQuery);
      setInventorys(response.data.rows);
      setTotal(response.data.total);
      setTotalPages(response.data.totalPages);
    } catch (error) {
      const errorMessage = "Gagal mengambil data inventaris!";
      toast.error(errorMessage);
    } finally {
      setLoadingState("loading", false);
    }
  }


  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setInventoryData((prevData) => ({...prevData, [name]: value }));

    const { code, price, stock_quantity } = { ...inventoryData, [name]: value };
    
    const isButtonDisabled = 
      !(code && code.length == 5) || 
      !(price >= 1000) ||              
      !(stock_quantity > 0);      

    setIsButtonDisabled(isButtonDisabled);
  };

  const handleCancel = () => {
    navigate(-1);
  };

  const handleCreate = (e) => {
    e.preventDefault();
    handleCreateInventory(inventoryData);
  };

  const confirmDelete = (id) => {
    setSelectedInventoryId(id);
    setShowModal(true);
  };

  const cancelDelete = () => {
    setShowModal(false);
    setSelectedInventoryId(null);
  };

  const proceedDelete = () => {
    handleDeleteInventory(selectedInventoryId);
    setShowModal(false);
    setSelectedInventoryId(null);
  };

  useEffect(() => {
    handleGetInventorys();
  }, [page, query]);

  return {
    inventorys,
    page,
    setPage,
    total,
    totalPages,
    setQuery,
    handleGetInventorys,
    handleDeleteInventory,
    inventoryData,
    setInventoryData,
    handleGetInventoryById,
    handleUpdateInventory,
    handleCreateInventory,
    image,
    handleImageChange,
    inputFileRef,
    isBusy,
    handleInputChange,
    handleCancel,
    handleCreate,
    showModal,
    setShowModal,
    selectedInventoryId,
    setSelectedInventoryId,
    confirmDelete,
    cancelDelete,
    proceedDelete,
    isButtonDisabled,
    filterQuery,
    setFilterQuery,
    handleFilterInventorys,
  };
};

export default useInventoryHook;