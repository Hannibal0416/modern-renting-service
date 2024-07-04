import React, { useState } from 'react';
import styled from 'styled-components';
import { useNavigate } from "react-router-dom"
import { useSelector, useDispatch } from "react-redux"

import LoginForm from './LoginForm';
import UserButton from './UserButton';

const LogoH1 = styled.h1`
  position: relative;
  font-weight: 600;
  font-size: 20px;
  color: #2276a9;
  max-width: 1280px;
  margin: 0 auto;

  span {
    cursor: pointer;
  }

  button {
    color: #333;
    font-size: 15px;
    font-weight: 900;
  }
}
`

// Define a type for the component props
type HeaderBarProps = {
  title: string;
};

//style="position: absolute; inset: 0px auto auto 0px; margin: 0px; transform: translate(1206px, 44px);"
const HeaderBar: React.FC<HeaderBarProps> = ({ title }) => {
  const [ open, setOpen ] = useState(false)
  
  const users = useSelector( state=> state.users)
  const navigate = useNavigate()
  const dispatch = useDispatch()

  const isLogin = users.userName
  const logOut = () => {
    dispatch({
      type: 'LOGOUT_USER',
    })

    localStorage.removeItem('access_token')
    navigate("/")
  }

  return (
    <>
      <div className="bg-white text-gray-800 text-xl text-left py-4 px-6 fixed top-0 left-0 right-0 z-10 shadow-md">
        <LogoH1>
          <span onClick={() => {navigate("/")}}  className="font-semibold poetsen-one-regular">{title}</span>

          {isLogin &&
            <UserButton users={users} logOut={logOut}/>
          }

          {!isLogin &&
            <button
              color="black"
              style={{ outline: 0 }}
              onClick={() => setOpen(true)}
              className="absolute right-5 cursor-pointer border-0 active:border-0 hover:border-0"
            >Sign Up / Log In</button>
          }
        </LogoH1>        
      </div>

      {open &&
        <LoginForm
          open={open}
          closeModal={() => setOpen(false)}
        />
      }
    </>
  );
};

export default HeaderBar;