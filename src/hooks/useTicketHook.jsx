import { useState, useEffect, useCallback, useRef } from "react";
import { useNavigate } from "react-router-dom";
import Modal from "react-modal"; 
import {
  getTickets,
  deleteTicket,
  getTicketById,
  updateTicket,
  createTicket,
  filterTickets,
} from "../services/api/apiTicket";
import { toast } from "react-toastify";
Modal.setAppElement("#root");

const useTicketHook = () => {
  const [isButtonDisabled, setIsButtonDisabled] = useState(true);
  const [tickets, setTickets] = useState([]);
  const [page, setPage] = useState(1);
  const [query, setQuery] = useState("");
  const [filterQuery, setFilterQuery] = useState("");
  const [total, setTotal] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const navigate = useNavigate();
  const [ticketData, setTicketData] = useState({
    name: "",
    price: "",
    stock_quantity: "",
    visitor_quantity: "",
    description: "",
  });

  const [loading, setLoading] = useState(false);
  const [uploading, setUploading] = useState(false);
  const [updating, setUpdating] = useState(false);
  const isBusy = () => loading || uploading || updating;

  const [showModal, setShowModal] = useState(false); 
  const [selectedTicketId, setSelectedTicketId] = useState(null); 


  const setLoadingState = (type, value) => {
    if (type === "loading") {
      setLoading(value);
    } else if (type === "uploading") {
      setUploading(value);
    } else if (type === "updating") {
      setUpdating(value);
    }
  };


  const handleGetTickets = async () => {
    setLoadingState("loading", true);
    try {
      const response = await getTickets(page, query);
      setTickets(response.data.rows);
      setTotal(response.data.total);
      setTotalPages(response.data.totalPages);
    } catch (error) {
      const errorMessage = "Gagal mengambil data tiket!";
      toast.error(errorMessage);
    } finally {
      setLoadingState("loading", false);
    }
  };

  const handleDeleteTicket = async (id) => {

      setLoadingState("loading", true);
      try {
        await deleteTicket(id);
        toast.success("Tiket berhasil dihapus!");
        handleGetTickets();
      } catch (error) {
        const errorMessage = "Gagal menghapus tiket. Silakan coba lagi!";
        toast.error(errorMessage);
      } finally {
        setLoadingState("loading", false);
        setShowModal(false); 
      }
    
  };

  const handleGetTicketById = useCallback(async (id) => {
    setLoadingState("loading", true);
    try {
      const response = await getTicketById(id);
      setTicketData(response.data);
    } catch (error) {
      const errorMessage = "Gagal mengambil data tiket!";
      
      toast.error(errorMessage);
    } finally {
      setLoadingState("loading", false);
    }
  }, []);

  const handleUpdateTicket = async (id) => {
    setLoadingState("updating", true);
    
    const formattedPrice = ticketData.price.includes(".") && ticketData.price.split(".")[1] === "00"
      ? ticketData.price.split(".")[0]
      : ticketData.price;
  
    const updatedTicketData = {
      ...ticketData,
      price: formattedPrice,
    };
  
    try {
      await updateTicket(id, updatedTicketData);
      toast.success("Tiket berhasil diperbarui!");
      handleGetTickets();
      navigate(-1);
    } catch (error) {
      const errorMessage = "Gagal memperbarui tiket!";
      toast.error(errorMessage);
    } finally {
      setLoadingState("updating", false);
    }
  };
  

  const handleCreateTicket = async () => {
    setLoadingState("updating", true);
    try {
      await createTicket(ticketData);
      toast.success("Tiket berhasil ditambahkan!");
      setTicketData({
        name: "",
        price: "",
        stock_quantity: "",
        visitor_quantity: "",
        description: "",
      });
      handleGetTickets();
    } catch (error) {
      const errorMessage = "Gagal menambahkan tiket!";
      toast.error(errorMessage);
    } finally {
      setLoadingState("updating", false);
    }
  };

  const handleFilterTickets = async () => {
    setLoadingState("loading", true);
    try {
      const response = await filterTickets(page, filterQuery);
      setTickets(response.data.rows);
      setTotal(response.data.total);
      setTotalPages(response.data.totalPages);
    } catch (error) {
      const errorMessage = "Gagal mengambil data tiket!";
      toast.error(errorMessage);
    } finally {
      setLoadingState("loading", false);
    }
  }


  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setTicketData((prevData) => ({ ...prevData, [name]: value }));

    const { price, stock_quantity, visitor_quantity } = { ...ticketData, [name]: value };

    const isButtonDisabled =
      !(price >= 1000) ||
      !(stock_quantity >= 1) ||
      !(visitor_quantity >= 1 <= 5);

    setIsButtonDisabled(isButtonDisabled);

  };

  const handleCancel = () => {
    navigate(-1);
  };

  const handleCreate = (e) => {
    e.preventDefault();
    handleCreateTicket(ticketData);
  };

  const confirmDelete = (id) => {
    setSelectedTicketId(id);
    setShowModal(true);
  };

  const cancelDelete = () => {
    setShowModal(false);
    setSelectedTicketId(null);
  };

  const proceedDelete = () => {
    handleDeleteTicket(selectedTicketId);
    setShowModal(false);
    setSelectedTicketId(null);
  };

  useEffect(() => {
    handleGetTickets();
  }, [page, query]);

  return {
    tickets,
    page,
    setPage,
    total,
    totalPages,
    setQuery,
    handleGetTickets,
    handleDeleteTicket,
    ticketData,
    setTicketData,
    handleGetTicketById,
    handleUpdateTicket,
    handleCreateTicket,
    isBusy,
    handleInputChange,
    handleCancel,
    handleCreate,
    showModal,
    setShowModal,
    selectedTicketId,
    setSelectedTicketId,
    confirmDelete,
    cancelDelete,
    proceedDelete,
    isButtonDisabled,
    filterQuery,
    setFilterQuery,
    handleFilterTickets,
  };
};

export default useTicketHook;