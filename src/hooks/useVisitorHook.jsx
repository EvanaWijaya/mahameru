import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Modal from "react-modal"; 
import {
  getVisitors,
  filterVisitors,
} from "../services/api/apiVisitor";
import { toast } from "react-toastify";
Modal.setAppElement("#root");

const useVisitorHook = () => {
  const [visitors, setVisitors] = useState([]);
  const [page, setPage] = useState(1);
  const [query, setQuery] = useState("");
  const [filterQuery, setFilterQuery] = useState("");
  const [total, setTotal] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const navigate = useNavigate();
  const [visitorData, setVisitorData] = useState({
    visitor_id: "",
    visitor_name: "",
    visitor_nik: "",
    ticket_id: "",
    ticket_name: "",
  });

  const [loading, setLoading] = useState(false);
  const [uploading, setUploading] = useState(false);
  const [updating, setUpdating] = useState(false);
  const isBusy = () => loading || uploading || updating;

  const setLoadingState = (type, value) => {
    if (type === "loading") {
      setLoading(value);
    } else if (type === "uploading") {
      setUploading(value);
    } else if (type === "updating") {
      setUpdating(value);
    }
  };


  const handleGetVisitors = async () => {
    setLoadingState("loading", true);
    try {
      const response = await getVisitors(page, query);
      setVisitors(response.data.rows);
      setTotal(response.data.total);
      setTotalPages(response.data.totalPages);
    } catch (error) {
      const errorMessage = "Gagal mengambil data pengunjung!";
      toast.error(errorMessage);

    } finally {
      setLoadingState("loading", false);
    }
  };

    const handleFilterVisitors = async () => {
      setLoadingState("loading", true);
      try {
        const response = await filterVisitors(page, filterQuery);
        setVisitors(response.data.rows);
        setTotal(response.data.total);
        setTotalPages(response.data.totalPages);
      } catch (error) {
        const errorMessage = "Gagal mengambil data pengunjung!";
        toast.error(errorMessage);
      } finally {
        setLoadingState("loading", false);
      }
    };
  
  const handleCancel = () => {
    navigate(-1);
  };


  useEffect(() => {
    handleGetVisitors();
  }, [page, query]);

  return {
    visitors,
    page,
    setPage,
    total,
    totalPages,
    setQuery,
    handleGetVisitors,
    visitorData,
    setVisitorData,
    isBusy,
    handleCancel,
    filterQuery,
    setFilterQuery,
    handleFilterVisitors,
  };
};

export default useVisitorHook;