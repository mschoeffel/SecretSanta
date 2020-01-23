import Vue from 'vue'
import Vuetify from 'vuetify'
import VueI18n from 'vue-i18n'

import HelpDef from '@/components/HelpDef'

import { mount, createLocalVue } from '@vue/test-utils';

const localVue = createLocalVue()

Vue.use(VueI18n)
const i18n = new VueI18n({});

describe('HelpDef.vue', () => {
  let vuetify

  beforeEach(() => {
    vuetify = new Vuetify()
  })

  const mountFunction = options => {
    return mount(HelpDef, {
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

})
