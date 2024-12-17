import { useState, useEffect, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import Modal from "react-modal";
import {
  getTransactions,
  getTransactionById,
  getTotalTransaction,
  getTotalTransactionTicket,
  getTotalTransactionInventory,
  getTotalTransactionSuccess,
  filterTransactions,
  getTransactionSellingSummary
} from "../services/api/apiTransaction";
import { toast } from "react-toastify";
Modal.setAppElement("#root");

const useTransactionHook = () => {
  const [transactions, setTransactions] = useState([]);
  const [page, setPage] = useState(1);
  const [query, setQuery] = useState("");
  const [filterQuery, setFilterQuery] = useState("");
  const [total, setTotal] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const navigate = useNavigate();
  const [transactionData, setTransactionData] = useState({
    transaction_id: "",
    transaction_date: "",
    username: "",
    ticket_name: "",
    ticket_price: "",
    transaction_status: "",
    ticket_quantity: "",
  });

  const [detailTransactionData, setDetailTransactionData] = useState({
    transaction_number: "",
    username: "",
    date: "",
    payment_method: "",
    total_price: "",
    status: "",
    tickets: [
      ""
    ]
  });

  const [totalTransactionSuccess, setTotalTransactionSuccess] = useState({
    transaction_total: "",
  });

  const[totalTransaction, setTotalTransaction] = useState({
    total_price_amount: "",
  });

  const[totalTransactionTicket, setTotalTransactionTicket] = useState({
    total_price_tickets: "",
  });

  const[totalTransactionInventory, setTotalTransactionInventory] = useState({
    total_price_inventories: "",
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


  const handleGetTransactions = async () => {
    setLoadingState("loading", true);
    try {
      const response = await getTransactions(page, query);
      setTransactions(response.data.rows);
      setTotal(response.data.total);
      setTotalPages(response.data.totalPage);
    } catch (error) {
      const errorMessage = "Gagal mengambil data transaksi!";
      toast.error(errorMessage);

    } finally {
      setLoadingState("loading", false);
    }
  };

  const handleFilterTransactions = async () => {
    setLoadingState("loading", true);
    try {
      const response = await filterTransactions(page, filterQuery);
      setTransactions(response.data.rows);
      setTotal(response.data.total);
      setTotalPages(response.data.totalPage);
    } catch (error) {
      const errorMessage = "Gagal mengambil data transaksi!";
      toast.error(errorMessage);
    } finally {
      setLoadingState("loading", false);
    }
  };

  const handleGetTransactionById = useCallback(async (id) => {
    setLoadingState("loading", true);
    try {
      const response = await getTransactionById(id);
      setDetailTransactionData(response.data);

    } catch (error) {
      const errorMessage = "Gagal mengambil data transaksi!";
      toast.error(errorMessage);
    } finally {
      setLoadingState("loading", false);
    }
  }, []);

  const handleGetTotalTransaction = async () => {
    setLoadingState("loading", true);
    try {
      const response = await getTotalTransaction(page, query);
      setTotalTransaction(response.data);
    } catch (error) {
      const errorMessage = "Gagal mengambil data transaksi!";
      toast.error(errorMessage);
    } finally {
      setLoadingState("loading", false);
    }
  };

  const handleGetTotalTransactionTicket = async () => {
    setLoadingState("loading", true);
    try {
      const response = await getTotalTransactionTicket(page, query);
      setTotalTransactionTicket(response.data);
    } catch (error) {
      const errorMessage = "Gagal mengambil data transaksi!";
      toast.error(errorMessage);
    } finally {
      setLoadingState("loading", false);
    }
  }

  const handleGetTotalTransactionInventory = async () => {
    setLoadingState("loading", true);
    try {
      const response = await getTotalTransactionInventory(page, query);
      setTotalTransactionInventory(response.data);
    } catch (error) {
      const errorMessage = "Gagal mengambil data transaksi!";
      toast.error(errorMessage);
    } finally {
      setLoadingState("loading", false);
    }
  }

  const handleGetTotalTransactionSuccess = async () => {
    setLoadingState("loading", true);
    try {
      const response = await getTotalTransactionSuccess(page, query);
      setTotalTransactionSuccess(response.data);
    } catch (error) {
      const errorMessage = "Gagal mengambil data transaksi!";
      toast.error(errorMessage);
    } finally {
      setLoadingState("loading", false);
    }
  }

  const getWeekStartAndEndDates = () => {
    const today = new Date();
    const currentYear = today.getFullYear();
    const currentMonth = today.getMonth();
  
    const startOfMonth = new Date(currentYear, currentMonth, 1);
    const daysInMonth = new Date(currentYear, currentMonth + 1, 0).getDate();
  
    const weeks = [];
    for (let i = 1; i <= daysInMonth; i += 7) {
      const weekStart = new Date(currentYear, currentMonth, i);
      const weekEnd = new Date(currentYear, currentMonth, Math.min(i + 6, daysInMonth));
      weeks.push({
        startDate: formatDate(weekStart),
        endDate: formatDate(weekEnd),
      });
    }
  
    return weeks;
  };
  
  const formatDate = (date) => {
    const yyyy = date.getFullYear();
    let mm = date.getMonth() + 1;
    let dd = date.getDate();
  
    if (dd < 10) dd = `0${dd}`;
    if (mm < 10) mm = `0${mm}`;
  
    return `${yyyy}-${mm}-${dd}`;
  };
  
  const handleGetTransactionSummary = async () => {
    setLoadingState("loading", true);
  
    const weeks = getWeekStartAndEndDates();
    const labels = weeks.map((_, index) => `Minggu ${index + 1}`);
  
    try {
      const promises = weeks.map(({ startDate, endDate }) =>
        getTransactionSellingSummary(startDate, endDate)
      );
  
      const responses = await Promise.all(promises);
  
      const revenueData = responses.map((response) => parseFloat(response.data.total_revenue));
      const ticketData = responses.map((response) => parseInt(response.data.total_tickets_sold));
  
      updateCharts(labels, revenueData, ticketData);
    } catch (error) {
      const errorMessage = "Gagal mengambil data transaksi!";
      toast.error(errorMessage);
    } finally {
      setLoadingState("loading", false);
    }
  };
  
  const [chartData, setChartData] = useState({
    financialData: {
      labels: [],
      datasets: [
        {
          label: "Pemasukan Weekday",
          data: [],
          borderColor: "rgb(75, 192, 192)",
          tension: 0.1,
        },
        {
          label: "Pemasukan Weekend",
          data: [],
          borderColor: "rgb(255, 159, 64)",
          tension: 0.1,
        },
      ],
    },
    ticketData: {
      labels: [],
      datasets: [
        {
          label: "Tiket Terjual Weekday",
          data: [],
          borderColor: "rgb(75, 192, 192)",
          tension: 0.1,
        },
        {
          label: "Tiket Terjual Weekend",
          data: [],
          borderColor: "rgb(255, 159, 64)",
          tension: 0.1,
        },
      ],
    },
  });
  
  const updateCharts = (labels, revenueData, ticketData) => {
    const weekdayRevenue = revenueData.map((revenue, index) => (index % 7 < 5 ? revenue : 0));
    const weekendRevenue = revenueData.map((revenue, index) => (index % 7 >= 5 ? revenue : 0));
    const weekdayTickets = ticketData.map((tickets, index) => (index % 7 < 5 ? tickets : 0));
    const weekendTickets = ticketData.map((tickets, index) => (index % 7 >= 5 ? tickets : 0));
  
    setChartData({
      financialData: {
        labels,
        datasets: [
          {
            label: "Pemasukan Weekday",
            data: weekdayRevenue,
            borderColor: "rgb(75, 192, 192)",
            tension: 0.1,
          },
          {
            label: "Pemasukan Weekend",
            data: weekendRevenue,
            borderColor: "rgb(255, 159, 64)",
            tension: 0.1,
          },
        ],
      },
      ticketData: {
        labels,
        datasets: [
          {
            label: "Tiket Terjual Weekday",
            data: weekdayTickets,
            borderColor: "rgb(75, 192, 192)",
            tension: 0.1,
          },
          {
            label: "Tiket Terjual Weekend",
            data: weekendTickets,
            borderColor: "rgb(255, 159, 64)",
            tension: 0.1,
          },
        ],
      },
    });
  };
  

  const handleCancel = () => {
    navigate(-1);
  };


  useEffect(() => {
    handleGetTransactions();
    handleGetTotalTransaction();
    handleGetTotalTransactionTicket();
    handleGetTotalTransactionInventory();
    handleGetTotalTransactionSuccess();
  }, [page, query]);

  return {
    transactions,
    page,
    setPage,
    total,
    totalPages,
    setQuery,
    handleGetTransactions,
    transactionData,
    setTransactionData,
    isBusy,
    handleCancel,
    handleGetTransactionById,
    detailTransactionData,
    setDetailTransactionData,
    totalTransaction,
    handleGetTotalTransaction,
    setTotalTransaction,
    totalTransactionTicket,
    handleGetTotalTransactionTicket,
    setTotalTransactionTicket,
    totalTransactionInventory,
    handleGetTotalTransactionInventory,
    setTotalTransactionInventory,
    totalTransactionSuccess,
    handleGetTotalTransactionSuccess,
    setTotalTransactionSuccess,
    filterQuery,
    setFilterQuery,
    handleFilterTransactions,
    handleGetTransactionSummary,
    chartData, 
    
  };
};

export default useTransactionHook;