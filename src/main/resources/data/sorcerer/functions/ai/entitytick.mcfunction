#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s[tag=sorcererstaffmodel] at @s run function sorcerer:ai/__generated__/block/163
execute as @s[tag=sorcerermodel] at @s run function sorcerer:ai/__generated__/block/165
execute as @s[tag=sorcerermodel] unless score SorcererDebug value matches 1 at @s run function sorcerer:ai/__generated__/block/166
execute as @s[tag=sorcerermodel] if score SorcererIdle value matches 1.. run function sorcerer:ai/__generated__/block/174
execute as @s[tag=sorcerermodel] at @s unless entity @a[distance=..100] run function sorcerer:ai/__generated__/block/175
execute at @s[tag=sorcerermodel] run bossbar set minecraft:sorcerer players @a[distance=..80]
execute as @s[tag=hitbox,tag=sorcerer] at @s run function sorcerer:ai/__generated__/block/176