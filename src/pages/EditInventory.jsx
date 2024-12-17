import React, { useEffect, useCallback } from "react";
import { useParams } from "react-router-dom";
import PageHeader from "../components/PageHeader";
import InventoryForm from "../components/inventory/InventoryForm";
import useInventoryHook from "../hooks/useInventoryHook";

const EditInventory = () => {
  const { id } = useParams();
  const {
    inventoryData,
    handleGetInventoryById,
    image,
    handleImageChange,
    inputFileRef,
    handleCancel,
    handleInputChange,
    handleUpdateInventory,
    isButtonDisabled,

  } = useInventoryHook();

  const fetchInventory = useCallback(() => {
    handleGetInventoryById(id);
  }, [id, handleGetInventoryById]);

  const handleSave = async (e) => {
    e.preventDefault();
    await handleUpdateInventory(id, inventoryData);
  }
  
  useEffect(() => {
    fetchInventory();
  }, [fetchInventory]);

  return (
    <div className="flex-1 min-h-screen p-4 py-10 overflow-auto bg-gray-100 lg:ml-64">
      <PageHeader
        title="Edit Inventaris"
        subtitle="Ini adalah halaman untuk memperbarui data inventaris."
      />
      <InventoryForm
        inventoryData={inventoryData}
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

export default EditInventory;
