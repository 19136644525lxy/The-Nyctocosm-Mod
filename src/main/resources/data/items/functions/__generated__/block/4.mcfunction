#built using mc-build (https://github.com/mc-build/mc-build)

function spells:ring/add
playsound nyctocosm:spells.ring.absorb master @a[distance=..10] ~ ~ ~ 1
tag @s add collect_soul