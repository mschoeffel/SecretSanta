import Vue from 'vue'
import Vuetify from 'vuetify'
import VueI18n from 'vue-i18n'

import NewGroupDef from '@/components/NewGroupDef'

import { mount, createLocalVue } from '@vue/test-utils';

const localVue = createLocalVue()

Vue.use(VueI18n)
const i18n = new VueI18n({});

describe('NewGroupDef.vue', () => {
  let vuetify

  beforeEach(() => {
    vuetify = new Vuetify()
  })

  const mountFunction = options => {
    return mount(NewGroupDef, {
      localVue,
      vuetify,
      i18n,
      ...options,
    })
  }

  it('should render correct group creation form', () => {
    const wrapper = mountFunction()

    expect(wrapper.html()).toMatchSnapshot()
    expect(wrapper.vm.step).toBe(1)
  })

  it('should give invalid input to empty form', () =>{
    const wrapper = mountFunction()

    wrapper.find('v-btn[name=next]').trigger('click')

    expect(wrapper.vm.step).toBe(1)
    expect(wrapper.vm.groupnameerror).toBe("new-group.validation-groupnamerequired")
    expect(wrapper.vm.validationerror).toBe("new-group.validationerror")
  })
  
  
  it('should give invalid input to empty groupname', () =>{
    const wrapper = mountFunction()

    wrapper.setData({groupname: ""})
    wrapper.find('v-btn[name=next]').trigger('click')

    expect(wrapper.vm.step).toBe(1)
    expect(wrapper.vm.groupnameerror).toBe("new-group.validation-groupnamerequired")
    expect(wrapper.vm.validationerror).toBe("new-group.validationerror")
  })

  
  it('should give invalid input to too long groupname', () =>{
    const wrapper = mountFunction()

    wrapper.setData({groupname: "0123456789123456"})
    wrapper.find('v-btn[name=next]').trigger('click')

    expect(wrapper.vm.step).toBe(1)
    expect(wrapper.vm.groupnameerror).toBe("new-group.validation-groupnamelength")
    expect(wrapper.vm.validationerror).toBe("new-group.validationerror")
  })

  
  it('should give invalid input to 0 member', () =>{
    const wrapper = mountFunction()

    wrapper.setData({membercount: 0})
    wrapper.find('v-btn[name=next]').trigger('click')

    expect(wrapper.vm.step).toBe(1)
    expect(wrapper.vm.membercounterror).toBe("new-group.validation-membercountbounds")
    expect(wrapper.vm.validationerror).toBe("new-group.validationerror")
  })

  it('should give invalid input to 1 member', () =>{
    const wrapper = mountFunction()

    wrapper.setData({membercount: 1})
    wrapper.find('v-btn[name=next]').trigger('click')

    expect(wrapper.vm.step).toBe(1)
    expect(wrapper.vm.membercounterror).toBe("new-group.validation-membercountbounds")
    expect(wrapper.vm.validationerror).toBe("new-group.validationerror")
  })

  it('should give invalid input to too much member', () =>{
    const wrapper = mountFunction()

    wrapper.setData({membercount: 51})
    wrapper.find('v-btn[name=next]').trigger('click')

    expect(wrapper.vm.step).toBe(1)
    expect(wrapper.vm.membercounterror).toBe("new-group.validation-membercountbounds")
    expect(wrapper.vm.validationerror).toBe("new-group.validationerror")
  })

  it('should give invalid input to negative rerolls', () =>{
    const wrapper = mountFunction()

    wrapper.setData({rerolls: -1})
    wrapper.find('v-btn[name=next]').trigger('click')

    expect(wrapper.vm.step).toBe(1)
    expect(wrapper.vm.rerollserror).toBe("new-group.validation-rerollsbounds")
    expect(wrapper.vm.validationerror).toBe("new-group.validationerror")
  })

  it('should give invalid input to too much rerolls', () =>{
    const wrapper = mountFunction()

    wrapper.setData({rerolls: 101})
    wrapper.find('v-btn[name=next]').trigger('click')

    expect(wrapper.vm.step).toBe(1)
    expect(wrapper.vm.rerollserror).toBe("new-group.validation-rerollsbounds")
    expect(wrapper.vm.validationerror).toBe("new-group.validationerror")
  })

})
