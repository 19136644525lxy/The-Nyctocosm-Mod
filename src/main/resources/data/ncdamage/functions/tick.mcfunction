#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @a[tag=ncd.shouldremoveattributes] ncd.tstlhu 1
execute as @a[tag=ncd.shouldremoveattributes] if score @s ncd.tstlhu matches 2.. run function ncdamage:__generated__/block/0