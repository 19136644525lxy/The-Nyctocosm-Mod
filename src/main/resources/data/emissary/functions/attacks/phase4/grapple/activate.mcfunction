#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add grappletry
tag @s add quickturn
kill @e[tag=emissary,tag=flytarget]
execute at @a[sort=random,gamemode=!spectator,tag=!emissarydebug,distance=..100] run summon marker ~ ~0.7 ~ {Tags:["emissary","flytarget"]}