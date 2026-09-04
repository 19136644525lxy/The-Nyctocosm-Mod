#built using mc-build (https://github.com/mc-build/mc-build)

execute at @e[tag=portal,tag=origin,tag=activating] run function nyctocosm:portal/activation/z/summon_external_resonances_first
execute as @e[tag=resonator] at @s rotated as @e[tag=portal,tag=origin] run tp @s ~ ~ ~ ~ ~
scoreboard players set @e[tag=resonator,tag=first] timer -900
schedule function nyctocosm:portal/activation/z/__generated__/sequence/1 16t replace
schedule function nyctocosm:portal/activation/z/__generated__/sequence/2 32t replace