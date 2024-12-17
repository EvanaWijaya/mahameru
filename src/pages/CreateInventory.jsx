import React from "react";
import PageHeader from "../components/PageHeader";
import InventoryForm from "../components/inventory/InventoryForm";
import useInventoryHook from "../hooks/useInventoryHook";

const CreateInventory = () => {
  const { inventoryData, handleCreate, handleImageChange, handleInputChange, handleCancel, image, inputFileRef, isButtonDisabled} = useInventoryHook();

  return (
    <div className="flex-1 min-h-screen p-4 py-10 overflow-auto bg-gray-100 lg:ml-64">
      <PageHeader
        title="Kelola Inventaris"
        subtitle="Ini adalah halaman untuk mengelola data inventory."
      />
      <InventoryForm
        inventoryData={inventoryData}
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

export default CreateInventory;
