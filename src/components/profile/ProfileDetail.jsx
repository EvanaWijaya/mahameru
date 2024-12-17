import React from 'react';

const ProfileDetail = ({ label, value }) => {
  return (
    <div className="flex items-center justify-between pb-4 border-b">
      <p className="font-semibold">{label}</p>
      <p className="text-gray-600">{value || '-'}</p>
    </div>
  );
};

export default ProfileDetail;