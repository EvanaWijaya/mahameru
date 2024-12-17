import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import PageHeader from "../components/PageHeader";
import Pagination from "../components/Pagination";
import InventoryTable from "../components/inventory/InventoryTable";
import SearchBar from "../components/SearchBar";
import useInventoryHook from "../hooks/useInventoryHook";
import ConfirmModal from "../components/ConfirmModal";

const InventoryData = () => {
  const navigate = useNavigate();
  const {
    inventorys,
    page,
    setPage,
    total,
    totalPages,
    setQuery,
    isBusy,
    setFilterQuery,
    handleFilterInventorys,
    showModal,
    confirmDelete,
    cancelDelete,
    proceedDelete,
  } = useInventoryHook();

  const [isFilterOpen, setIsFilterOpen] = useState(false);
  const [activeSort, setActiveSort] = useState(null);

  const handleFilterInventorysSelect = (sortOrder) => {
    setFilterQuery(sortOrder);
    setActiveSort(sortOrder);
    setIsFilterOpen(false);
    handleFilterInventorys(sortOrder);
  };

  const createInventoryRoute = () => {
    navigate(`/kelola-inventaris`);
  };

  const updateInventoryRoute = (id) => {
    navigate(`/edit-inventaris/${id}`);
  };

  return (
    <div className="flex-1 min-h-screen p-4 py-10 bg-gray-100 lg:ml-64">
      <PageHeader
        title="Data Inventaris"
        subtitle="Ini adalah halaman untuk melihat data inventaris Agrowisata Tepas Papandayan."
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
              d="M9 12h3.75M9 15h3.75M9 18h3.75m3 .75H18a2.25 2.25 0 0 0 2.25-2.25V6.108c0-1.135-.845-2.098-1.976-2.192a48.424 48.424 0 0 0-1.123-.08m-5.801 0c-.065.21-.1.433-.1.664 0 .414.336.75.75.75h4.5a.75.75 0 0 0 .75-.75 2.25 2.25 0 0 0-.1-.664m-5.8 0A2.251 2.251 0 0 1 13.5 2.25H15c1.012 0 1.867.668 2.15 1.586m-5.8 0c-.376.023-.75.05-1.124.08C9.095 4.01 8.25 4.973 8.25 6.108V8.25m0 0H4.875c-.621 0-1.125.504-1.125 1.125v11.25c0 .621.504 1.125 1.125 1.125h9.75c.621 0 1.125-.504 1.125-1.125V9.375c0-.621-.504-1.125-1.125-1.125H8.25ZM6.75 12h.008v.008H6.75V12Zm0 3h.008v.008H6.75V15Zm0 3h.008v.008H6.75V18Z"
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
                  onClick={() => handleFilterInventorysSelect("ASC")}
                >
                  A-Z
                </button>
                <button
                  className={`block w-full px-4 py-2 text-sm text-left text-gray-700 hover:bg-gray-100 ${
                    activeSort === "DESC" ? "bg-[#00796B] text-white" : ""
                  }`}
                  onClick={() => handleFilterInventorysSelect("DESC")}
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
            <p>Tidak ada data inventaris saat ini. Silakan tambahkan inventaris baru.</p>
          </div>
        ) : (
          <InventoryTable
            inventorys={inventorys}
            onDelete={confirmDelete}
            onEdit={updateInventoryRoute}
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
          onClick={createInventoryRoute}
        >
          Kelola Inventaris
        </button>
      </div>
      <ConfirmModal
        isOpen={showModal}
        onRequestClose={cancelDelete}
        onConfirm={proceedDelete}
        title="Konfirmasi Hapus"
        message="Apakah Anda yakin ingin menghapus inventaris ini?"
      />
    </div>
  );
};

export default InventoryData;
