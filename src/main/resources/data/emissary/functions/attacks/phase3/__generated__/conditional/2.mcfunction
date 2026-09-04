#built using mc-build (https://github.com/mc-build/mc-build)

function animated_java:ajemissary/animations/pause_all
execute facing entity @a[sort=nearest,limit=1,gamemode=!spectator,tag=!emissarydebug] feet rotated ~ 0 run tp @s ~ ~ ~ ~ ~
execute at @s run function emissary:attacks/phase3/choose
scoreboard players set #execute LANG_MC_INTERNAL 1