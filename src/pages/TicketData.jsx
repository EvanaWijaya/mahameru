import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import PageHeader from "../components/PageHeader";
import Pagination from "../components/Pagination";
import TicketTable from "../components/ticket/TicketTable";
import SearchBar from "../components/SearchBar";
import useTicketHook from "../hooks/useTicketHook";
import ConfirmModal from "../components/ConfirmModal";

const TicketData = () => {
  const navigate = useNavigate();
  const {
    tickets,
    page,
    setPage,
    total,
    totalPages,
    setQuery,
    isBusy,
    setFilterQuery,
    handleFilterTickets,
    showModal,
    confirmDelete,
    cancelDelete,
    proceedDelete,
  } = useTicketHook();

  const [isFilterOpen, setIsFilterOpen] = useState(false);
  const [activeSort, setActiveSort] = useState(null);

  const handleFilterTicketsSelect = (sortOrder) => {
    setFilterQuery(sortOrder);
    setActiveSort(sortOrder);
    setIsFilterOpen(false);
    handleFilterTickets(sortOrder);
  };

  const createTicketRoute = () => {
    navigate(`/kelola-tiket`);
  };

  const updateTicketRoute = (id) => {
    navigate(`/edit-tiket/${id}`);
  };

  return (
    <div className="flex-1 min-h-screen p-4 py-10 bg-gray-100 lg:ml-64">
      <PageHeader
        title="Data Tiket"
        subtitle="Ini adalah halaman untuk melihat data tiket Agrowisata Tepas Papandayan."
        icon={
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            strokeWidth={1.5}
            stroke="currentColor"
            className="w-5 h-5"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              d="M16.023 9.348h4.992v-.001M2.985 19.644v-4.992m0 0h4.992m-4.993 0 3.181 3.183a8.25 8.25 0 0 0 13.803-3.7M4.031 9.865a8.25 8.25 0 0 1 13.803-3.7l3.181 3.182m0-4.991v4.99"
            />
          </svg>
        }
      />
      <div className="overflow-hidden bg-white rounded-lg shadow-sm">
        <div className="p-4 border-b">
          <div className="flex flex-col gap-4 md:flex-row">
            <button
              onClick={() => setIsFilterOpen((prev) => !prev)}
              className="flex items-center justify-center w-full gap-2 px-4 py-2 text-gray-600 rounded-lg bg-gray-50 md:w-auto md:justify-start"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                strokeWidth={1.5}
                stroke="currentColor"
                className="w-5 h-5"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  d="M3 4a1 1 0 011-1h16a1 1 0 011 1v2.586a1 1 0 01-.293.707l-6.414 6.414a1 1 0 00-.293.707V17l-4 4v-6.586a1 1 0 00-.293-.707L3.293 7.293A1 1 0 013 6.586V4z"
                />
              </svg>
              Filter
            </button>
            {isFilterOpen && (
              <div className="absolute z-10 w-48 p-2 mt-2 bg-white border rounded shadow-lg">
                <button
                  className={`block w-full px-4 py-2 text-sm text-left text-gray-700 hover:bg-gray-100 ${
                    activeSort === "ASC" ? "bg-[#00796B] text-white" : ""
                  }`}
                  onClick={() => handleFilterTicketsSelect("ASC")}
                >
                  A-Z
                </button>
                <button
                  className={`block w-full px-4 py-2 text-sm text-left text-gray-700 hover:bg-gray-100 ${
                    activeSort === "DESC" ? "bg-[#00796B] text-white" : ""
                  }`}
                  onClick={() => handleFilterTicketsSelect("DESC")}
                >
                  Z-A
                </button>
              </div>
            )}
            <SearchBar
              onQueryChange={(query) => setQuery(query)}
              onSearch={(e) => {
                e.preventDefault();
                setPage(1);
              }}
            />
          </div>
        </div>
        {total === 0 && !isBusy() ? (
          <div className="p-4 text-center text-gray-600">
            <p>Tidak ada data tiket saat ini. Silakan tambahkan tiket baru.</p>
          </div>
        ) : (
          <TicketTable
            tickets={tickets}
            onDelete={confirmDelete}
            onEdit={updateTicketRoute}
            isBusy={isBusy}
          />
        )}
        {total > 0 && (
          <div className="flex flex-col items-end justify-between gap-4 p-4 md:flex-row">
            <p className="text-sm text-gray-600">
              Showing {(page - 1) * 4 + 1} to {Math.min(page * 4, total)} of {total} entries
            </p>
            <Pagination
              page={page}
              totalPages={totalPages}
              onPageChange={setPage}
              onPrevious={() => setPage((prev) => Math.max(prev - 1, 1))}
              onNext={() => setPage((prev) => Math.min(prev + 1, totalPages))}
            />
          </div>
        )}
      </div>
      <div className="flex justify-center mt-4">
        <button
          className="w-full px-6 py-2 text-white bg-teal-600 rounded-lg hover:bg-teal-700 md:w-auto"
          onClick={createTicketRoute}
        >
          Kelola Tiket
        </button>
      </div>
      <ConfirmModal
        isOpen={showModal}
        onRequestClose={cancelDelete}
        onConfirm={proceedDelete}
        title="Konfirmasi Hapus"
        message="Apakah Anda yakin ingin menghapus tiket ini?"
      />
    </div>
  );
};

export default TicketData;
