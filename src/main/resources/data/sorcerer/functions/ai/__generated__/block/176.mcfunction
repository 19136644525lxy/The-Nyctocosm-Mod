#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s[tag=sorcererhitbox] at @e[tag=sorcerermodel] positioned ~ ~0.6 ~ run tp @s ~ ~ ~ ~ ~
execute as @s[tag=sorcererstaffhitbox] at @e[tag=aj.ajnullpoint.root] run tp @s ~ ~0.6 ~ ~ ~
effect clear @s minecraft:glowing
execute as @s[tag=sorcererhitbox,nbt={HurtTime:10s},tag=!invul] run function sorcerer:ai/__generated__/block/177
execute as @s[tag=sorcererstaffhitbox,nbt={HurtTime:10s},tag=!invul] run function sorcerer:ai/__generated__/block/178
execute store result score @s ns.health run data get entity @s Health