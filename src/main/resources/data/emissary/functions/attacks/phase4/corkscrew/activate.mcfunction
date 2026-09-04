#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add corkscrewing
tag @s add quickturn
kill @e[tag=emissary,tag=flytarget]
execute at @a[gamemode=!spectator,tag=!emissarydebug,distance=..300,sort=random] run summon marker ~ ~ ~ {Tags:["emissary","flytarget"]}