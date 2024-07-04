import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'

type UserButtonProps = {
    users: any;
    logOut: Function;
  };

const UserButton: React.FC<UserButtonProps> = ({users, logOut}) => {
    const [openMenu, setOpenMenu] = useState(false)

    const navigate = useNavigate()

    const handleOpenMenu = () => {
        setOpenMenu(!openMenu)
    }

    const changePage = (e) => {
        e.stopPropagation()

        setOpenMenu(false)
        navigate("/profile")
    }

    const _logout = (e) => {
        e.stopPropagation()
        logOut()
    }
    
  return (
    <>
        <div className="absolute top-0 right-5 cursor-pointer border-0 active:border-0 hover:border-0" onClick={handleOpenMenu}>
            <div>
                <div>
                    <button className="pr-2 bg-transparent border border-primary text-primary px-5 py-1 can-hover:hover:bg-opacity-20 active:bg-opacity-20 can-hover:hover:bg-primary active:bg-primary rounded-full inline-flex items-center justify-center whitespace-nowrap select-none text-center truncate">
                        <span className="inline-flex items-center space-x-2">{users.email}
                        <i className="icon-bars"></i>
                        <i className="icon-user text-lg"></i>
                        </span>
                    </button>
                </div>

                {openMenu &&
                    <div className="menu absolute right-0" >
                        <div className="origin-top">
                        <div className="rounded-2xl bg-white shadow border border-neutral-300 w-full max-h-full overflow-auto">
                            <div className="flex flex-col p-4 w-56">
                            <div className="flex-col font-medium">
                                <p className="mb-4 text-sm">Hi {users.userName}</p>
                                    
                                <div className="space-y-2">
                                <a onClick={changePage} className="bg-transparent border border-primary text-primary px-5 py-1 can-hover:hover:bg-opacity-20 active:bg-opacity-20 can-hover:hover:bg-primary active:bg-primary rounded-full block w-full whitespace-nowrap select-none text-center truncate text-sm">Profile</a>
                                <a className="block text-center text-primary can-hover:hover:opacity-75 text-sm" onClick={_logout}>Sign Out</a>
                                </div>
                            </div>
                            </div>
                        </div>
                        </div>
                    </div>
                }
            
            </div>
        </div>
    </>
  );
};

export default UserButton;