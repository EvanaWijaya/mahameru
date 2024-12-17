import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import PageHeader from "../components/PageHeader";
import Pagination from "../components/Pagination";
import EmployeeTable from "../components/employee/EmployeeTable";
import SearchBar from "../components/SearchBar";
import useEmployeeHook from "../hooks/useEmployeeHook";
import ConfirmModal from "../components/ConfirmModal";

const EmployeeData = () => {
  const navigate = useNavigate();
  const {
    employees,
    page,
    setPage,
    total,
    totalPages,
    setQuery,
    isBusy,
    setFilterQuery,
    handleFilterEmployees,
    showModal,
    confirmDelete,
    cancelDelete,
    proceedDelete,
  } = useEmployeeHook();

  const [isFilterOpen, setIsFilterOpen] = useState(false);
  const [activeSort, setActiveSort] = useState(null);

  const handleFilterEmployeesSelect = (sortOrder) => {
    setFilterQuery(sortOrder);
    setActiveSort(sortOrder);
    setIsFilterOpen(false);
    handleFilterEmployees(sortOrder);
  };

  const createEmployeeRoute = () => {
    navigate(`/kelola-pegawai`);
  };

  const updateEmployeeRoute = (id) => {
    navigate(`/edit-pegawai/${id}`);
  };

  return (
    <div className="flex-1 min-h-screen p-4 py-10 bg-gray-100 lg:ml-64">
      <PageHeader
        title="Data Pegawai"
        subtitle="Ini adalah halaman untuk melihat data pegawai Agrowisata Tepas Papandayan."
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
              d="M15 19.128a9.38 9.38 0 0 0 2.625.372 9.337 9.337 0 0 0 4.121-.952 4.125 4.125 0 0 0-7.533-2.493M15 19.128v-.003c0-1.113-.285-2.16-.786-3.07M15 19.128v.106A12.318 12.318 0 0 1 8.624 21c-2.331 0-4.512-.645-6.374-1.766l-.001-.109a6.375 6.375 0 0 1 11.964-3.07M12 6.375a3.375 3.375 0 1 1-6.75 0 3.375 3.375 0 0 1 6.75 0Zm8.25 2.25a2.625 2.625 0 1 1-5.25 0 2.625 2.625 0 0 1 5.25 0Z"
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
                  onClick={() => handleFilterEmployeesSelect("ASC")}
                >
                  A-Z
                </button>
                <button
                  className={`block w-full px-4 py-2 text-sm text-left text-gray-700 hover:bg-gray-100 ${
                    activeSort === "DESC" ? "bg-[#00796B] text-white" : ""
                  }`}
                  onClick={() => handleFilterEmployeesSelect("DESC")}
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
            <p>Tidak ada data pegawai saat ini. Silakan tambahkan pegawai baru.</p>
          </div>
        ) : (
          <EmployeeTable
            employees={employees}
            onDelete={confirmDelete}
            onEdit={updateEmployeeRoute}
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
          onClick={createEmployeeRoute}
        >
          Kelola Pegawai
        </button>
      </div>
      <ConfirmModal
        isOpen={showModal}
        onRequestClose={cancelDelete}
        onConfirm={proceedDelete}
        title="Konfirmasi Hapus"
        message="Apakah Anda yakin ingin menghapus pegawai ini?"
      />
    </div>
  );
};

export default EmployeeData;
