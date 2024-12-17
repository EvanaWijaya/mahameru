import React, { useEffect, useCallback } from "react";
import { useParams } from "react-router-dom";
import PageHeader from "../components/PageHeader";
import TicketForm from "../components/ticket/TicketForm";
import useTicketHook from "../hooks/useTicketHook";

const EditTicket = () => {
  const { id } = useParams();
  const {
    ticketData,
    handleGetTicketById,
    handleCancel,
    handleInputChange,
    handleUpdateTicket,
    isButtonDisabled,
  } = useTicketHook();

  const fetchTicket = useCallback(() => {
    handleGetTicketById(id);
  }, [id, handleGetTicketById]);

  const handleSave = async (e) => {
    e.preventDefault();
    await handleUpdateTicket(id, ticketData);
  }
  
  useEffect(() => {
    fetchTicket();
  }, [fetchTicket]);

  return (
    <div className="flex-1 min-h-screen p-4 py-10 overflow-auto bg-gray-100 lg:ml-64">
      <PageHeader
        title="Edit Tiket"
        subtitle="Ini adalah halaman untuk memperbarui data tiket."
      />
      <TicketForm
        ticketData={ticketData}
        onChange={handleInputChange}
        onSubmit={handleSave}
        onCancel={handleCancel}
        isEdit={true}
        isButtonDisabled={isButtonDisabled}
      />
    </div>
  );
};

export default EditTicket;
