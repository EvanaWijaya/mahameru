import React from 'react';
import ProfileDetail from './ProfileDetail';

const ProfileInfo = ({ profile, onEditClick }) => {
    return (
        <div className="p-6 mb-6 bg-white rounded-lg shadow-sm">
            <h3 className="mb-6 text-xl font-bold text-center">Informasi Pribadi</h3>
            <div className="flex flex-col space-y-4">
                <ProfileDetail label="Username" value={profile.username} />
                <ProfileDetail label="Nomor Telepon" value={profile.phone_number} />
                <ProfileDetail label="Email" value={profile.email} />
                <div className="flex items-center justify-between pb-4 border-b">
                    <p className="font-semibold">Password</p>
                    <p className="text-gray-600">••••••••</p> { }
                </div>
                <ProfileDetail label="Alamat" value={profile.address || '-'} />
                <ProfileDetail label="Bio" value={profile.bio || '-'} />
            </div>
            <button className="px-4 py-2 m-5 mx-auto text-white bg-teal-600 rounded" onClick={onEditClick}>
                Edit
            </button>
        </div>
    );
};

export default ProfileInfo;