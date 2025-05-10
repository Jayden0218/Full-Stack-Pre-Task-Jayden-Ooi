<script setup lang="ts">
import { TresCanvas, useRenderLoop, extend } from '@tresjs/core'
import { shallowRef, ref, onMounted, watch, computed } from 'vue'
import { OrbitControls } from '@tresjs/cientos'
import gsap from 'gsap'
import { FontLoader } from 'three/examples/jsm/loaders/FontLoader'
import { TextGeometry } from 'three-stdlib'

// Extend Tres to recognize TextGeometry
extend({ TextGeometry })

const font = ref(null)
const textArray = ['M','A','N','A', 'G', 'E', 'M', 'E', 'N', 'T', 'F', 'I', 'L', 'E']
const zs = [-4.5,-4,-3.5,-3,-2.5,-2,-1.5,-1,-0.5,0,1,1.5, 2,2.5,  ] 


// const zs = []
// for (let z = -4.5; z <= 2.5; z++) {
//   zs.push(z)
// }

const fontOptions = {
  size: 0.5,
  height: 0.2,
  curveSegments: 5,
  bevelEnabled: true,
  bevelThickness: 0.05,
  bevelSize: 0.02,
  bevelOffset: 0,
  bevelSegments: 4,
}

const boxesRef = shallowRef()

// Load font on mount
onMounted(async () => {
  const loader = new FontLoader()
  try {
    font.value = await new Promise((resolve, reject) => {
      loader.load(
        'https://raw.githubusercontent.com/Tresjs/assets/main/fonts/FiraCodeRegular.json',
        resolve,
        undefined,
        reject
      )
    })
  } catch (err) {
    console.error('Font load failed:', err)
  }
})

// Animate once boxes are mounted
watch(boxesRef, () => {
  const positions = Array.from(boxesRef.value.children).map(c => c.position)
  const rotations = Array.from(boxesRef.value.children).map(c => c.rotation)

  const animProps = {
    ease: 'power1.inOut',
    duration: 1,
    stagger: { each: 0.25, repeat: -1, yoyo: true }
  }

  gsap.to(positions, { y: 1.5, ...animProps })
  gsap.to(rotations, { x: 1, ...animProps })
})
</script>

<template>
  <TresCanvas clear-color="dodgerblue">
    <TresPerspectiveCamera :position="[-10, 6, -7]" />
    <OrbitControls />
    <TresGroup ref="boxesRef">
      <TresMesh
        v-for="(char, i) in textArray"
        :key="i"
        :position="[0, 0.5, zs[i]]"
        :rotation="[0,-Math.PI/2, 2 * Math.PI]"
      >
        <TresTextGeometry
          v-if="font"
          :args="[char, { font, ...fontOptions }]"
        />
        <TresMeshNormalMaterial />
      </TresMesh>
    </TresGroup>
    <TresMesh v-if="font" 
        :position="[-1.5, 0, -2]"
        :rotation="[-Math.PI / 2, 0, -Math.PI / 2]">
      <TresTextGeometry
        :args="['SYSTEM', { font, ...fontOptions }]"
        
      />
      <TresMeshNormalMaterial />
    </TresMesh>

    <TresMesh :rotation="[-Math.PI / 2, 0, 0]" :position="[0, 0, 0]">
      <TresPlaneGeometry :args="[4, 10]" />
      <TresMeshBasicMaterial :color="'deepskyblue'" />
    </TresMesh>
    
  </TresCanvas>
</template>
