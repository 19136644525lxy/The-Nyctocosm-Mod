#built using mc-build (https://github.com/mc-build/mc-build)

clear @s glass_bottle 1
function spells:bottle/give
playsound minecraft:item.bottle.fill_dragonbreath master @a[distance=..10] ~ ~ ~ 1
tag @s add collect_soul