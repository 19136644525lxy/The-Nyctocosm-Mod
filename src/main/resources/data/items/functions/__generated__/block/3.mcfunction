#built using mc-build (https://github.com/mc-build/mc-build)

function spells:bottle/add
playsound minecraft:item.bottle.fill_dragonbreath master @a[distance=..10] ~ ~ ~ 1
tag @s add collect_soul