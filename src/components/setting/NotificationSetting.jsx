import React from 'react';

const NotificationSetting = ({ initialStatus, onNotificationToggle }) => {
  const handleNotificationToggle = (event) => {
    const newStatus = event.target.checked;
    onNotificationToggle(newStatus); 
  };

  return (
    <div className="p-6 mb-6 bg-white rounded-lg shadow-sm">
      <h2 className="mb-4 text-lg font-semibold">Pengaturan Notifikasi</h2>
      <div className="space-y-4">
        <div className="flex items-center justify-between">
          <span className="text-gray-600">Notifikasi di Website</span>
          <label className="relative inline-flex items-center cursor-pointer">
            <input
              type="checkbox"
              className="sr-only peer"
              checked={initialStatus}
              onChange={handleNotificationToggle} 
            />
            <div className="w-11 h-6 bg-gray-200 peer-focus:outline-none rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-teal-600"></div>
          </label>
        </div>
        <div className="flex items-center justify-between">
          <span className="text-gray-600">Bunyi Notifikasi</span>
          <select className="px-4 py-2 bg-gray-100 rounded">
            <option>(Default)</option>
          </select>
        </div>
      </div>
    </div>
  );
};

export default NotificationSetting;
