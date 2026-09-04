#built using mc-build (https://github.com/mc-build/mc-build)

function animated_java:ajemissary/animations/pause_all
scoreboard players set #tween_duration aj.i 0
function animated_java:ajemissary/animations/phase4.attack.apocalypse_rain/play
kill @e[tag=emissary,tag=flytarget]
execute at @a[distance=..100,sort=random,limit=1,gamemode=!spectator,tag=!emissarydebug] positioned ~ ~30 ~ run summon marker ~ ~ ~ {Tags:["emissary","flytarget"]}