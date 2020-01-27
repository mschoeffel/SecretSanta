import axios from 'axios'

const AXIOS = axios.create({
    baseURL: `/api`,
    timeout: 1000
});

export default {
    //NEW
    createGroup(name, rerolls, members) {
        return AXIOS.post('/v1/group/', {
            name: name,
            rerolls: rerolls,
            members: members
        });
    },
    checkGroupName(name) {
        return AXIOS.get('/v1/group/check/' + name);
    },
    drawPartner(groupname, name, key) {
        return AXIOS.post('/v1/groupmember/draw', {
            groupname: groupname,
            name: name,
            key: key
        });
    },
    getPartner(groupname, name, key) {
        return AXIOS.post('/v1/groupmember/partner', {
            groupname: groupname,
            name: name,
            key: key
        });
    },
    getMember(groupname, name, key) {
        return AXIOS.post('/v1/groupmember/member', {
            groupname: groupname,
            name: name,
            key: key
        });
    },
    acceptPartner(groupname, name, key) {
        return AXIOS.post('/v1/groupmember/accept', {
            groupname: groupname,
            name: name,
            key: key
        });
    },
    getGroupDetail(groupname, grouptoken){
        return AXIOS.get('/v1/group/' + groupname + '/' + grouptoken);
    },
    deleteGroup(groupname, grouptoken){
        return AXIOS.delete('/v1/group/' + groupname + '/' + grouptoken);
    }
}


