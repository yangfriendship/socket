<template>
	<div class="flex">
		<button @click="send">test</button>
	</div>
</template>
<script setup lang="ts">
const { $socket } = useSocket();
const send = () => {
	$socket.send("/echo", { test: "test" });
};
</script>
<style lang="scss"></style>
