#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add bladehit
scoreboard players operation @s ncd_damage = frozenbladesdamage value
scoreboard players operation @s ncd_damage *= 1000 value
scoreboard players operation damageAmt value = @s ncd_damage
tag @e[tag=sorcerer,tag=sorcerermodel,limit=1] add damager
execute as @s run function sorcerer:attacks/frozenblades/__generated__/block/96
effect give @s minecraft:slowness 10 1 true