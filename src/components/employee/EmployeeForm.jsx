import React from "react";

const EmployeeForm = ({ employeeData, onChange, onSubmit, onCancel, isEdit, handleImageChange, image, inputFileRef, isButtonDisabled }) => {
    return (
        <div className="p-4 mb-6 bg-white rounded-lg md:p-6">
            <h3 className="mb-6 text-lg font-bold">{isEdit ? "EDIT DATA" : "ENTRI DATA"}</h3>
            <div className="space-y-4">
                <div>
                    <label className="block mb-2 text-sm font-semibold">NIP</label>
                    <input
                        type="text"
                        name="nip"
                        value={employeeData.nip || ''}
                        onChange={onChange}
                        placeholder="Masukan NIP"
                        className="w-full px-3 py-2 text-sm border rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500"
                        maxLength={18}
                        minLength={18}
                    />
                </div>

                <div className="grid grid-cols-1 gap-4 sm:grid-cols-2">
                    <div>
                        <label className="block mb-2 text-sm font-semibold">NAMA</label>
                        <input
                            type="text"
                            name="name"
                            value={employeeData.name || ''}
                            onChange={onChange}
                            placeholder="Masukan Nama"
                            className="w-full px-3 py-2 text-sm border rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500"
                            minLength={1}
                            maxLength={50}
                        />
                    </div>
                    <div>
                        <label className="block mb-2 text-sm font-semibold">POSISI</label>
                        <input
                            type="text"
                            name="position"
                            value={employeeData.position || ''}
                            onChange={onChange}
                            placeholder="Masukan Posisi"
                            className="w-full px-3 py-2 text-sm border rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500"
                        />
                    </div>
                </div>

                <div>
                    <label className="block mb-2 text-sm font-semibold">NO HP</label>
                    <input
                        type="number"
                        name="phone_number"
                        value={employeeData.phone_number || ''}
                        onChange={onChange}
                        placeholder="Masukan NO HP"
                        className="w-full px-3 py-2 text-sm border rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500"
                        minLength={10}
                        maxLength={15}
                    />
                </div>

                <div>
                    <label className="block mb-2 text-sm font-semibold">FOTO</label>
                    <input
                        type="file"
                        accept="image/*"
                        onChange={handleImageChange}
                        ref={inputFileRef}
                        className="w-full px-3 py-2 text-sm border rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500"
                    />
                    {image || employeeData.picture ? (
                        <div className="mt-4">
                            <img
                                src={image || employeeData.picture}
                                alt="Preview"
                                className="w-full max-w-md rounded"
                            />
                        </div>
                    ) : null}
                </div>

                <div className="flex flex-col justify-end gap-3 pt-4 sm:flex-row">
                    <button
                        className="flex items-center justify-center w-full gap-2 px-6 py-2 text-white transition-colors bg-red-500 rounded-lg sm:w-auto hover:bg-red-600"
                        onClick={onCancel}
                    >
                        <svg
                            className="w-5 h-5"
                            fill="none"
                            stroke="currentColor"
                            viewBox="0 0 24 24"
                        >
                            <path
                                strokeLinecap="round"
                                strokeLinejoin="round"
                                strokeWidth="2"
                                d="M10 19l-7-7m0 0l7-7m-7 7h18"
                            />
                        </svg>
                        Kembali
                    </button>
                    <button
                        className={`flex items-center justify-center w-full gap-2 px-6 py-2 text-white transition-colors rounded-lg sm:w-auto ${isButtonDisabled
                            ? 'bg-gray-300 text-gray-500 cursor-not-allowed'
                            : 'bg-teal-600 hover:bg-teal-700'
                            }`}
                        onClick={onSubmit}
                        disabled={isButtonDisabled}
                    >
                        <svg
                            className="w-5 h-5"
                            fill="none"
                            stroke="currentColor"
                            viewBox="0 0 24 24"
                        >
                            {isEdit ? (
                                <path fill="currentColor" d="M21 7v12q0 .825-.587 1.413T19 21H5q-.825 0-1.412-.587T3 19V5q0-.825.588-1.412T5 3h12zm-9 11q1.25 0 2.125-.875T15 15t-.875-2.125T12 12t-2.125.875T9 15t.875 2.125T12 18m-6-8h9V6H6z" />
                            ) : (
                                <path
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                    strokeWidth="2"
                                    d="M12 5v14m7-7H5"
                                />
                            )}
                        </svg>
                        {isEdit ? "Simpan" : "Tambah"}
                    </button>

                </div>
            </div>
        </div>
    );
};

export default EmployeeForm;
