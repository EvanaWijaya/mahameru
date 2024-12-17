import React, { useEffect, useCallback } from "react";
import { useParams } from "react-router-dom";
import PageHeader from "../components/PageHeader";
import EmployeeForm from "../components/employee/EmployeeForm";
import useEmployeeHook from "../hooks/useEmployeeHook";

const EditEmployee = () => {
  const { id } = useParams();
  const {
    employeeData,
    handleGetEmployeeById,
    image,
    handleImageChange,
    inputFileRef,
    handleCancel,
    handleInputChange,
    handleUpdateEmployee,
    isButtonDisabled,
  } = useEmployeeHook();

  const fetchEmployee = useCallback(() => {
    handleGetEmployeeById(id);
  }, [id, handleGetEmployeeById]);

  const handleSave = async (e) => {
    e.preventDefault();
    await handleUpdateEmployee(id, employeeData);
  }
  
  useEffect(() => {
    fetchEmployee();
  }, [fetchEmployee]);

  return (
    <div className="flex-1 min-h-screen p-4 py-10 overflow-auto bg-gray-100 lg:ml-64">
      <PageHeader
        title="Edit Pegawai"
        subtitle="Ini adalah halaman untuk memperbarui data pegawai."
      />
      <EmployeeForm
        employeeData={employeeData}
        onChange={handleInputChange}
        onSubmit={handleSave}
        onCancel={handleCancel}
        isEdit={true}
        handleImageChange={handleImageChange}
        image={image}
        inputFileRef={inputFileRef}
        isButtonDisabled={isButtonDisabled}
      />

    </div>
  );
};

export default EditEmployee;
