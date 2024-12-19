import { loginService, getAdminService, updateAdminService, addAdminPictureService } from '../services/webAdminServices.js';
import { addTicketService, searchTicketsService, getTicketByIdService, updateTicketService, deleteTicketService, getTicketsFilterService } from '../services/webTicketServices.js';
import { addInventoryService, searchInventoriesService, getInventoryByIdService, updateInventoryService, deleteInventoryService, getInventoriesFilterService } from '../services/webInventoryServices.js';
import { addEmployeeService, searchEmployeesService, getEmployeeByIdService, updateEmployeeService, deleteEmployeeService, getEmployeesFilterService } from '../services/webEmployeeServices.js';
import { searchVisitorsService, getVisitorsFilterService } from '../services/webVisitorServices.js';
import { getTransactionsTotalService, searchTransactionsService, getTransactionByIdService, getTransactionsTotalPriceAmountService, getTransactionsFilterService, getTransactionsTotalPriceAmountTicketsService, getTransactionsTotalPriceAmountInventoriesService, getSellingTicketsSummaryByDateService, getVisitorAmountByDateService } from '../services/webTransactionServices.js';

// User
const login = async (req, res) => {
  try {
    const data = await loginService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

const getAdmin = async (req, res) => {
  try {
    const data = await getAdminService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

const updateAdmin = async (req, res) => {
  try {
    const data = await updateAdminService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

const addAdminPicture = async (req, res) => {
  try {
    const data = await addAdminPictureService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

// Ticket
const addTicket = async (req, res) => {
  try {
    const data = await addTicketService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

const searchTickets = async (req, res) => {
  try {
    const data = await searchTicketsService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

const getTicketById = async (req, res) => {
  try {
    const data = await getTicketByIdService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

const updateTicket = async (req, res) => {
  try {
    const data = await updateTicketService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

const deleteTicket = async (req, res) => {
  try {
    const data = await deleteTicketService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
}

const getTicketsFilter = async (req, res) => {
  try {
    const data = await getTicketsFilterService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
}

// Inventory
const addInventory = async (req, res) => {
  try {
    const data = await addInventoryService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

const searchInventories = async (req, res) => {
  try {
    const data = await searchInventoriesService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

const getInventorybyId = async (req, res) => {
  try {
    const data = await getInventoryByIdService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

const updateInventory = async (req, res) => {
  try {
    const data = await updateInventoryService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

const deleteInventory = async (req, res) => {
  try {
    const data = await deleteInventoryService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

const getInventoriesFilter = async (req, res) => {
  try {
    const data = await getInventoriesFilterService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
}

// Employee
const addEmployee = async (req, res) => {
  try {
    const data = await addEmployeeService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

const searchEmployees = async (req, res) => {
  try {
    const data = await searchEmployeesService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

const getEmployeeById = async (req, res) => {
  try {
    const data = await getEmployeeByIdService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

const updateEmployee = async (req, res) => {
  try {
    const data = await updateEmployeeService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

const deleteEmployee = async (req, res) => {
  try {
    const data = await deleteEmployeeService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

const getEmployeesFilter = async (req, res) => {
  try {
    const data = await getEmployeesFilterService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
}

// Visitor
const searchVisitors = async (req, res) => {
  try {
    const data = await searchVisitorsService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
}

const getVisitorsFilter = async (req, res) => {
  try {
    const data = await getVisitorsFilterService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
}

// Transaction
const getTransactionsTotal = async (req, res) => {
  try {
    const data = await getTransactionsTotalService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
}

const searchTransactions = async (req, res) => {
  try {
    const data = await searchTransactionsService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
}

const getTransactionById = async (req, res) => {
  try {
    const data = await getTransactionByIdService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
}

const getTransactionsTotalPriceAmount = async (req, res) => {
  try {
    const data = await getTransactionsTotalPriceAmountService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
}

const getTransactionsFilter = async (req, res) => {
  try {
    const data = await getTransactionsFilterService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
}

const getTransactionsTotalPriceAmountTickets = async (req, res) => {
  try {
    const data = await getTransactionsTotalPriceAmountTicketsService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
}

const getTransactionsTotalPriceAmountInventories = async (req, res) => {
  try {
    const data = await getTransactionsTotalPriceAmountInventoriesService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
}

const getSellingTicketsSummaryByDate = async (req, res) => {
  try {
    const data = await getSellingTicketsSummaryByDateService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
}

const getVisitorAmountByDate = async (req, res) => {
  try {
    const data = await getVisitorAmountByDateService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
}

export default { 
  login,
  getAdmin, 
  updateAdmin,
  addAdminPicture,
  addTicket,
  searchTickets,
  getTicketById,
  updateTicket,
  deleteTicket,
  getTicketsFilter,
  addInventory,
  searchInventories,
  getInventorybyId,
  updateInventory,
  deleteInventory,
  getInventoriesFilter,
  addEmployee,
  searchEmployees,
  getEmployeeById,
  updateEmployee,
  deleteEmployee,
  getEmployeesFilter,
  searchVisitors,
  getVisitorsFilter,
  getTransactionsTotal,
  searchTransactions,
  getTransactionById,
  getTransactionsTotalPriceAmount,
  getTransactionsFilter,
  getTransactionsTotalPriceAmountTickets,
  getTransactionsTotalPriceAmountInventories,
  getSellingTicketsSummaryByDate,
  getVisitorAmountByDate,
};
