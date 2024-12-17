import React from "react";

const ActionButton = ({ type, onClick }) => {
  const icons = {
    edit: (
      <svg
        className="w-4 h-4"
        viewBox="0 0 24 24"
        fill="none"
        stroke="currentColor"
      >
        <path
          d="M15.232 5.232l3.536 3.536m-2.036-5.036a2.5 2.5 0 113.536 3.536L6.5 21.036H3v-3.572L16.732 3.732z"
          strokeWidth="2"
          strokeLinecap="round"
        />
      </svg>
    ),
    delete: (
      <svg
        className="w-4 h-4"
        viewBox="0 0 24 24"
        fill="none"
        stroke="currentColor"
      >
        <path
          d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"
          strokeWidth="2"
          strokeLinecap="round"
          strokeLinejoin="round"
        />
      </svg>
    ),
  };

  const colors = {
    edit: "bg-green-500 hover:bg-green-600",
    delete: "bg-orange-500 hover:bg-orange-600",
  };

  return (
    <button
      className={`p-2 text-white rounded-lg ${colors[type]}`}
      onClick={onClick}
    >
      {icons[type]}
    </button>
  );
};

export default ActionButton;
