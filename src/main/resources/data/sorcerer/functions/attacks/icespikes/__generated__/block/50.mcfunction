#built using mc-build (https://github.com/mc-build/mc-build)

execute as @a[distance=..2] run function sorcerer:attacks/icespikes/hit
execute as @s[scores={ns.icespikestimer=6}] run playsound minecraft:block.glass.break master @a[distance=..20] ~ ~ ~