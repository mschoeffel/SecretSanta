import axios from 'axios'

const AXIOS = axios.create({
  baseURL: `/api`,
  timeout: 1000
});


export default {
    hello() {
        return AXIOS.get(`/v0/hello`);
    },
    getUser(userId) {
        return AXIOS.get(`/v0/user/` + userId);
    },
    createUser(firstName, lastName) {
        return AXIOS.post(`/v0/user/` + firstName + '/' + lastName);
    },
    getSecured(user, password) {
        return AXIOS.get(`/v0/secured/`,{
            auth: {
                username: user,
                password: password
            }});
    },
    //NEW
    createGroup(name, rerolls, members){
        return AXIOS.post('/v1/group/', {
            name: name,
            rerolls: rerolls,
            members: members
        });
    },
    checkGroupName(name){
        return AXIOS.get('/v1/group/check/'+ name);
    },
    drawPartner(groupname, name, key){
        return AXIOS.post('/v1/groupmember/draw', {
            groupname: groupname,
            name: name,
            key: key
        });
    }
}


