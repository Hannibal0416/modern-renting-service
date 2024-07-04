const initState = {
    userName: '',
    firstName: '',
    lastName: '',
    email: '',
    birthday: '',
    idNumber: '',
    phone: '',
}

const Users = (state = initState, action: any) => {
    switch(action.type) {
        case 'GET_DATA':
            return {
                ...state,
                ...action.data,
            }

        case 'UPDATE_DATA':
            return {
                ...state,
                ...action.data,
            }

        case 'LOGOUT_USER':
            return initState
        
        default:
            return state
    }
}

export default Users
