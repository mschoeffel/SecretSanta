import Vue from 'vue'
import Vuetify from 'vuetify'
import VueI18n from 'vue-i18n'

import HomeDef from '@/components/HomeDef'

import { mount, createLocalVue } from '@vue/test-utils';

const localVue = createLocalVue()

Vue.use(VueI18n)
const i18n = new VueI18n({});

describe('HomeDef.vue', () => {
  let vuetify

  beforeEach(() => {
    vuetify = new Vuetify()
  })

  const mountFunction = options => {
    return mount(HomeDef, {
      localVue,
      vuetify,
      i18n,
      ...options,
    })
  }

  it('should render correct home form', () => {
    const wrapper = mountFunction()

    expect(wrapper.html()).toMatchSnapshot()
  })

  it('should give invalid input to empty form', () =>{
    const wrapper = mountFunction()

    wrapper.find('v-btn[id=next]').trigger('click')

    expect(wrapper.vm.step).toBe(1)
    expect(wrapper.vm.groupnameerror).toBe("home.validation-groupnamerequired")
    expect(wrapper.vm.membernameerror).toBe("home.validation-membernamerequired")
    expect(wrapper.vm.keyerror).toBe("home.validation-keyrequired")
    expect(wrapper.vm.displayerror).toBe("home.validationerror")
  })
  
  it('should give invalid input to empty groupname', () =>{
    const wrapper = mountFunction()

    wrapper.setData({
        groupname: "",
        name: "abcdef",
        key: "123456"
    })
    wrapper.find('v-btn[id=next]').trigger('click')

    expect(wrapper.vm.step).toBe(1)
    expect(wrapper.vm.groupnameerror).toBe("home.validation-groupnamerequired")
    expect(wrapper.vm.membernameerror).toBe("")
    expect(wrapper.vm.keyerror).toBe("")
    expect(wrapper.vm.displayerror).toBe("home.validationerror")
  })

})
