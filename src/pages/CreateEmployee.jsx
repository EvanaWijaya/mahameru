import React from "react";
import PageHeader from "../components/PageHeader";
import EmployeeForm from "../components/employee/EmployeeForm";
import useEmployeeHook from "../hooks/useEmployeeHook";

const CreateEmployee = () => {
  const { employeeData, handleCreate, handleImageChange, handleInputChange, handleCancel, image, inputFileRef, isButtonDisabled} = useEmployeeHook();

  return (
    <div className="flex-1 min-h-screen p-4 py-10 overflow-auto bg-gray-100 lg:ml-64">
      <PageHeader
        title="Kelola Pegawai"
        subtitle="Ini adalah halaman untuk mengelola data pegawai."
      />
      <EmployeeForm
        employeeData={employeeData}
        onChange={handleInputChange}
        onSubmit={handleCreate}
        onCancel={handleCancel}
        isEdit={false}
        handleImageChange={handleImageChange}
        image={image}
        inputFileRef={inputFileRef}
        isButtonDisabled={isButtonDisabled}
      />
    </div>
  );
};

export default CreateEmployee;
