#built using mc-build (https://github.com/mc-build/mc-build)

execute as @e[tag=aj.ajsorcererm1.root] at @s run function sorcerer:__generated__/block/203
execute as @e[tag=aj.ajnullpoint.root] at @s run function sorcerer:__generated__/block/204
tp @e[tag=sorcererstaffhitbox] ~ -100 ~
kill @e[tag=sorcererstaffhitbox]
scoreboard players set Sorcerer ns.attacking 0