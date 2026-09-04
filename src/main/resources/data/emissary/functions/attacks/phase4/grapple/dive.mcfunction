#built using mc-build (https://github.com/mc-build/mc-build)

kill @e[tag=emissary,tag=flytarget]
execute at @s rotated ~ 50 positioned ^ ^ ^150 run summon marker ~ ~ ~ {Tags:["emissary","flytarget"]}