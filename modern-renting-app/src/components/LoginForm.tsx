import React, { useState } from 'react';
import styled from 'styled-components';
import axios from 'axios';
import { useDispatch } from 'react-redux'

import appleLogo from "../assets/apple.png";
import googleLogo from "../assets/google.png";
import facebookLogo from "../assets/facebook.png";

const Area = styled.div`
  height: 100%;
  left: 0;
  position: fixed;
  top: 0;
  width: 100%;
  z-index: 30;
`

const Overlay = styled.div`
  background-color: rgba(0,0,0,0.5);
  height: 100%;
  left: 0;
  position: fixed;
  top: 0;
  width: 100%;
  z-index: 30;
`

const Modal = styled.div`
  align-items: center;
  display: flex;
  justify-content: center;
  margin-left: auto;
  margin-right: auto;
  max-width: 100%;
  min-height: 100%;
  min-width: 8rem;
  padding: 1rem;
  position: relative;
`

const InputEmail = styled.input`
  border: 1px solid #ccc;
  padding: 5px 10px;
  border-radius: 5px;
  width: 100%;
  margin-top: 5px;
`

// Define a type for the component props
type LoginFormProps = {
  open: boolean;
  closeModal: Function;
};

const LoginForm: React.FC<LoginFormProps> = ({ open, closeModal }) => {
    const [ email, setEmail ] = useState('')
    const [ password, setPassword ] = useState('')
    const dispatch = useDispatch()

    const emailOnChange = (event: any) => {
        setEmail(event.target.value)
    }

    const pwdOnChange = (event: any) => {
      setPassword(event.target.value)
    }

    const getUser = async () => {
      await axios.get('/users')
      .then(async (response) => {
        dispatch({
          type: 'GET_DATA',
          data: response.data,
        })
      })
      .catch( (error) => console.log(error))
    }

    const _onLogin = async () => {
      await axios.post('/oauth2/token', { email, password })
      .then(async (response) => {
        const { access_token } = response.data

        if (access_token) {
          localStorage.setItem('access_token', access_token)
          await getUser()
        }

        closeModal()
      })
      .catch( (error) => console.log(error))

        // onLogin()
    }

  return (
    <>
      {open &&
        <Area>
          <Overlay onClick={closeModal}/>
          <Modal className="modal inline-block md:w-2/3 lg:w-1/3">
            <div className="rounded-2xl bg-white shadow border border-neutral-300 w-full relative z-30">
              <div className="flex flex-col items-center mt-3">
                <div>
                  <p>Log in or Sign up</p>
                </div>
              </div>

              <hr className="divider" />

              <div className="py-8 px-6 flex flex-col items-center -mt-4">
                <div className="w-full">
                  <div className="-mb-6">
                    <label className="label">
                      <div className="flex">
                        <div className="flex" >
                          <label className="text-base">Email</label>
                        </div>
                      </div>
                      
                      <InputEmail type="email" autoComplete="username" placeholder="Please enter your email address" value={email} onChange={emailOnChange} />
                      
                      <div className="mt-1 text-sm text-neutral-400" >
                        <span></span>
                      </div>
                    </label>

                    <label className="label">
                      <div className="flex">
                        <div className="flex" >
                          <label className="text-base">Password</label>
                        </div>
                      </div>
                      
                      <InputEmail type="password" placeholder="Please enter your password" value={password} onChange={pwdOnChange} />
                      
                      <div className="mt-1 text-sm text-neutral-400" >
                        <span></span>
                      </div>
                    </label>
                      
                    <button onClick={_onLogin} style={{background: "rgb(0 49 104)"}} className="my-6 font-medium bg-primary text-white px-5 py-1 rounded-full block w-full whitespace-nowrap select-none text-center truncate" >Continue</button>
                                      
                    <hr className="divider" />
                                
                    {/* <BtnOauth onClick={_onLogin} className="flex justify-center items-center border border-neutral-800 w-full py-2 my-6 rounded-lg relative">
                      <img src={appleLogo} />
                      <p className="text-base">
                      Continue with Apple
                      </p>
                    </BtnOauth>

                    <BtnOauth onClick={_onLogin} className="flex justify-center items-center border border-neutral-800 w-full py-2 my-6 rounded-lg relative">
                      <img src={googleLogo} />
                      <p className="text-base">
                      Continue with Google
                      </p>
                    </BtnOauth>

                    <BtnOauth onClick={_onLogin} className="flex justify-center items-center border border-neutral-800 w-full py-2 my-6 rounded-lg relative">
                      <img src={facebookLogo} />
                      <p className="text-base">
                      Continue with Facebook
                      </p>
                    </BtnOauth> */}

                  </div>
                </div>
              </div>
            </div>
          </Modal>
        </Area>
      }
    </>
  );
};

export default LoginForm;