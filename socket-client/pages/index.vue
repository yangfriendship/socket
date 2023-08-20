<template>
	<div class="flex">
		<button @click="send">test</button>
	</div>
</template>
<script setup lang="ts">
const { $socket } = useNuxtApp();
const send = () => {
	$socket.send("/pub/echo", "test");
};
</script>
<style lang="scss"></style>
