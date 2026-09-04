#built using mc-build (https://github.com/mc-build/mc-build)

kill @e[tag=emissary,tag=flytarget]
execute positioned ^ ^ ^50 run summon marker ~ ~ ~ {Tags:["emissary","flytarget"]}
execute at @s run playsound nyctocosm:emissary.attack.grapple.slam master @a[distance=..200] ~ ~ ~ 3