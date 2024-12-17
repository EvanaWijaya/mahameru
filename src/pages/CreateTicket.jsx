import React from "react";
import PageHeader from "../components/PageHeader";
import TicketForm from "../components/ticket/TicketForm";
import useTicketHook from "../hooks/useTicketHook";

const CreateTicket = () => {
  const { ticketData, handleCreate, handleInputChange, handleCancel, isButtonDisabled} = useTicketHook();

  return (
    <div className="flex-1 min-h-screen p-4 py-10 overflow-auto bg-gray-100 lg:ml-64">
      <PageHeader
        title="Kelola Tiket"
        subtitle="Ini adalah halaman untuk mengelola data tiket."
      />
      <TicketForm
        ticketData={ticketData}
        onChange={handleInputChange}
        onSubmit={handleCreate}
        onCancel={handleCancel}
        isEdit={false}
        isButtonDisabled={isButtonDisabled}
      />
    </div>
  );
};

export default CreateTicket;
