#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation @s ncd_damage = voidwavedamage value
scoreboard players operation @s ncd_damage *= 1000 value
scoreboard players operation damageAmt value = @s ncd_damage
tag @e[tag=sorcerer,tag=sorcerermodel,limit=1] add damager
execute as @s run function sorcerer:attacks/voidwave/__generated__/block/42
effect give @s minecraft:slowness 10 1 true
effect give @s minecraft:darkness 4 1 true