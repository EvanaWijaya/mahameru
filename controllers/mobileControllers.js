import { registerService, loginService, getUserService, updateUserService, updateUserPictureService, createProblemReportService, createNewPasswordService } from "../services/mobileUserServices.js";
import { addVisitorService, getVisitorsService, updateVisitorService, deleteVisitorService } from "../services/mobileVisitorServices.js";
import { getTicketsService, getTicketByIdService, getInventoriesService, createTransactionService, getTicketsHistoryService, createReviewRatingTicketService, getReviewRatingTicketService } from "../services/mobileTransactionServices.js";

// User
const register = async (req, res) => {
  try {
    const data = await registerService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

const login = async (req, res) => {
  try {
    const data = await loginService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

const getUser = async (req, res) => {
  try {
    const data = await getUserService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

const updateUser = async (req, res) => {
  try {
    const data = await updateUserService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

const updateUserPicture = async (req, res) => {
  try {
    const data = await updateUserPictureService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

const createProblemReport = async (req, res) => {
  try {
    const data = await createProblemReportService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
}

const createNewPassword = async (req, res) => {
  try {
    const data = await createNewPasswordService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
}

// Visitor
const addVisitor = async (req, res) => {
  try {
    const data = await addVisitorService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

const getVisitors = async (req, res) => {
  try {
    const data = await getVisitorsService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

const updateVisitor = async (req, res) => {
  try {
    const data = await updateVisitorService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

const deleteVisitor = async (req, res) => {
  try {
    const data = await deleteVisitorService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

// Transaction
const getTickets = async (req, res) => {
  try {
    const data = await getTicketsService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
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

const getInventories = async (req, res) => {
  try {
    const data = await getInventoriesService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

const createTransaction = async (req, res) => {
  try {
    const data = await createTransactionService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

const getTicketsHistory = async (req, res) => {
  try {
    const data = await getTicketsHistoryService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

const createReviewRatingTicket = async (req, res) => {
  try {
    const data = await createReviewRatingTicketService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
}

const getReviewRatingTicket = async (req, res) => {
  try {
    const data = await getReviewRatingTicketService(req);
    res.status(200).json({ data });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
}

export default {
  register,
  login,
  getUser,
  updateUser,
  updateUserPicture,
  addVisitor,
  getVisitors,
  updateVisitor,
  deleteVisitor,
  getTickets,
  getTicketById,
  getInventories,
  createTransaction,
  getTicketsHistory,
  createReviewRatingTicket,
  getReviewRatingTicket,
  createProblemReport,
  createNewPassword,
}