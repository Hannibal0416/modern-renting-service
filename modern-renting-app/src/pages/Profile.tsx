import React, { useState } from 'react'
import styled from 'styled-components'
import axios from 'axios'
import { useSelector, useDispatch } from 'react-redux'
import { useNavigate } from "react-router-dom"

const Container = styled.div`
  width: 90vw;
  max-width: 1280px;
  h3 {
    text-align: left;
  }
`

type inputElProps = {
  type: string;
  placeholder: string;
  value: string;
  onChange: Function;
  onSave: Function;
  onCancel: Function;
};

const InputEl: Reac.FC<inputElProps> = ({type, placeholder, value, onChange, onSave, onCancel}) => {

  const _onChange = (e) => {
    onChange(e.target.value)
  }

  return (
    <div  className="flex items-center justify-between">
      <div data-v-7d80c5b3="" className="flex-1" >
        <div className="flex">
          <label  className="label pr-8">
            <div  className="flex">
            </div>
            
            <input 
              type={type}
              placeholder={placeholder}
              className="field"
              style={{padding: '0 5px', border: '1px solid #ccc', borderRadius: '5px'}}
              value={value}
              onChange={_onChange}
            />
            
            <div  className="mt-1 text-sm text-neutral-400">
              <span></span>
            </div>
          </label>
        </div>
        <div>
          <button
            style={{border: '1px solid'}}
            className="bg-transparent border border-primary text-primary px-5 py-1 can-hover:hover:bg-opacity-20 active:bg-opacity-20 can-hover:hover:bg-primary active:bg-primary rounded-full inline-flex items-center justify-center whitespace-nowrap select-none text-center truncate"
            onClick={onSave}
          >Save</button>
        </div>
      </div>
      <div  className="relative">
        <a  className="font-medium cancel-btn bg-transparent font-sm hover:text-primary-hover active:text-primary-click text-primary inline-flex items-center justify-center cursor-pointer whitespace-nowrap select-none text-center truncate"
          onClick={onCancel}
        >Cancel</a>
      </div>
    </div>
  )
}

const INIT_OPEN = {
  userName: false,
  email: false,
  phone: false,
}

const Profile: React.FC = () => {
  // const navigate = useNavigate()
  const users = useSelector( state => state.users)
  const dispatch = useDispatch()

  const [ isEdit, setIsEdit ] = useState(INIT_OPEN)

  const [ formUser, setFormUser ] : [ any, Function ] = useState(users)

  const updateEdit = (key: string, open: boolean) => {
    const obj : any = {}
    obj[key] = open

    setIsEdit({
      ...isEdit,
      ...obj,
    })
  }

  const changeValue = (key: string, value: string) => {
    const obj : any = {}
    obj[key] = value

    setFormUser({
      ...formUser,
      ...obj,
    })
  }

  const save = async () => {
    await axios.put('/users', { ...formUser })
    .then(async (response) => {
      dispatch({
        type: 'UPDATE_DATA',
        data: response.data,
      })

      setIsEdit(INIT_OPEN)
    })
    .catch( (error) => console.log(error))
  }

  return (
    <Container >
      <div className="flex-1">
        <div>
          <div>
            <div data-v-7d80c5b3="" className="my-6 container flex flex-col">
              <div className="flex">
                <div className="mb-6 lg:mb-10">
                  <div className="flex flex-row space-x-2">
                    <span className="font-normal">Member</span>
                    <span className="font-normal">&gt;</span>
                    <span className="font-normal">Profile</span>
                  </div>
                  <h3 className="mt-1 font-bold text-2xl">Profile</h3>
                </div>
              </div>
              <div data-v-7d80c5b3="" className="wrapper">
                <div  data-v-7d80c5b3="" className="pb-10">
                  <h3 >Name</h3>

                  {isEdit.userName &&
                    <InputEl
                      type="text"
                      placeholder="User Name"
                      value={formUser.userName}
                      onChange={(value) => changeValue('userName', value)}
                      onSave={save}
                      onCancel={() => {updateEdit('userName', false)}}
                    />
                  }

                  {!isEdit.userName &&
                    <div  className="flex items-center justify-between">
                      <h4  className="pr-10 text-neutral-400">{formUser.userName}</h4>
                      <div  className="relative">
                        <a  className="font-medium bg-transparent font-sm hover:text-primary-hover active:text-primary-click text-primary inline-flex items-center justify-center cursor-pointer whitespace-nowrap select-none text-center truncate"
                          onClick={() => {updateEdit('userName', true)}}
                        >Edit</a>
                      </div>
                    </div>
                  }
                </div>

                <div  data-v-7d80c5b3="" className="pb-10">
                  <h3 >Email</h3>

                  {isEdit.email &&
                    <InputEl
                      type="email"
                      placeholder="Email"
                      value={formUser.email}
                      onChange={(value) => changeValue('email', value)}
                      onSave={save}
                      onCancel={() => {updateEdit('email', false)}}
                    />
                  }

                  {!isEdit.email &&
                    <div  className="flex items-center justify-between">
                      <h4  className="pr-10 text-neutral-400">{formUser.email}</h4>
                      <div  className="relative">
                        <a  className="font-medium bg-transparent font-sm hover:text-primary-hover active:text-primary-click text-primary inline-flex items-center justify-center cursor-pointer whitespace-nowrap select-none text-center truncate"
                          onClick={() => {updateEdit('email', true)}}
                        >Edit</a>
                      </div>
                    </div>
                  }
                </div>

                <div  data-v-7d80c5b3="" className="pb-10">
                  <h3 >Mobile Number</h3>

                  {isEdit.phone &&
                    <InputEl
                      type="text"
                      placeholder="Mobile Number"
                      value={formUser.phone}
                      onChange={(value) => changeValue('phone', value)}
                      onSave={save}
                      onCancel={() => {updateEdit('phone', false)}}
                    />
                  }

                  {!isEdit.phone &&
                    <div  className="flex items-center justify-between">
                      <h4  className="pr-10 text-neutral-400">{formUser.phone}</h4>
                      <div  className="relative">
                        <a  className="font-medium bg-transparent font-sm hover:text-primary-hover active:text-primary-click text-primary inline-flex items-center justify-center cursor-pointer whitespace-nowrap select-none text-center truncate"
                          onClick={() => {updateEdit('phone', true)}}
                        >Edit</a>
                      </div>
                    </div>
                  }
                </div>

                <div  data-v-7d80c5b3="" className="pb-10">
                  <h3 >ID Number</h3>
                  <div  className="flex items-center justify-between">
                    <h4  className="pr-10 text-neutral-400">***</h4>
                    <div  className="relative">
                      {/* <a  className="font-medium bg-transparent font-sm hover:text-primary-hover active:text-primary-click text-primary inline-flex items-center justify-center cursor-pointer whitespace-nowrap select-none text-center truncate">Edit</a> */}
                    </div>
                  </div>
                </div>
                
                <div  data-v-7d80c5b3="" className="pb-10">
                  <h3 >Passport</h3>
                  <div  className="flex items-center justify-between">
                    <h4  className="pr-10 text-neutral-400">***</h4>
                    <div  className="relative">
                      {/* <a  className="font-medium bg-transparent font-sm hover:text-primary-hover active:text-primary-click text-primary inline-flex items-center justify-center cursor-pointer whitespace-nowrap select-none text-center truncate">Edit</a> */}
                    </div>
                  </div>
                </div>
                
                <div  data-v-7d80c5b3="" className="pb-10">
                  <h3 >Birthday</h3>
                  <div  className="flex items-center justify-between">
                    <h4  className="pr-10 text-neutral-400">***</h4>
                    <div  className="relative">
                      {/* <a  className="font-medium bg-transparent font-sm hover:text-primary-hover active:text-primary-click text-primary inline-flex items-center justify-center cursor-pointer whitespace-nowrap select-none text-center truncate">Edit</a> */}
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </Container>
  );
};

export default Profile;