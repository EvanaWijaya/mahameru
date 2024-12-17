import React from "react";
import Modal from "react-modal";

const ConfirmModal = ({ isOpen, onRequestClose, onConfirm, title, message }) => {
  return (
    <Modal
      isOpen={isOpen}
      onRequestClose={onRequestClose}
      contentLabel={title}
      className="max-w-full p-4 mx-auto mt-20 bg-white rounded-lg shadow-md w-96"
      overlayClassName="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center"
    >
      <h2 className="mb-4 text-lg font-semibold text-gray-700">{title}</h2>
      <p className="mb-6 text-gray-600">{message}</p>
      <div className="flex justify-end gap-4">
        <button
          onClick={onRequestClose}
          className="px-4 py-2 text-gray-600 bg-gray-200 rounded hover:bg-gray-300"
        >
          Batal
        </button>
        <button
          onClick={onConfirm}
          className="px-4 py-2 text-white bg-red-600 rounded hover:bg-red-700"
        >
          Hapus
        </button>
      </div>
    </Modal>
  );
};

export default ConfirmModal;
