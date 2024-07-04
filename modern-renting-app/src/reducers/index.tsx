import UsersReducer from './users'
import { combineReducers } from 'redux'

const allReducers = combineReducers({
    users: UsersReducer,
})

export default allReducers
