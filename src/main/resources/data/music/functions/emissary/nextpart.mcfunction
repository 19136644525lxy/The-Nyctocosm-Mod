#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s[scores={music.emissary.queue=1},tag=!music.queuepopped] run function music:emissary/phase/1/start
execute as @s[scores={music.emissary.queue=2},tag=!music.queuepopped] run function music:emissary/phase/1/continue
execute as @s[scores={music.emissary.queue=3},tag=!music.queuepopped] run function music:emissary/phase/2/start
execute as @s[scores={music.emissary.queue=4},tag=!music.queuepopped] run function music:emissary/phase/2/continue
execute as @s[scores={music.emissary.queue=5},tag=!music.queuepopped] run function music:emissary/phase/3/start
execute as @s[scores={music.emissary.queue=6},tag=!music.queuepopped] run function music:emissary/phase/3/continue
execute as @s[scores={music.emissary.queue=7},tag=!music.queuepopped] run function music:emissary/phase/4/start
execute as @s[scores={music.emissary.queue=8},tag=!music.queuepopped] run function music:emissary/phase/4/continue
execute as @s[scores={music.emissary.queue=9},tag=!music.queuepopped] run function music:emissary/phase/5/start
execute as @s[scores={music.emissary.queue=10},tag=!music.queuepopped] run function music:emissary/phase/5/continue
execute as @s[scores={music.emissary.queue=11},tag=!music.queuepopped] run function music:emissary/phase/6/start
execute as @s[scores={music.emissary.queue=12},tag=!music.queuepopped] run function music:emissary/phase/6/continue
execute as @s[scores={music.emissary.queue=13},tag=!music.queuepopped] run function music:emissary/phase/7/start
execute as @s[scores={music.emissary.queue=14},tag=!music.queuepopped] run function music:emissary/phase/7/continue
execute as @s[scores={music.emissary.queue=15},tag=!music.queuepopped] run function music:emissary/phase/8/start
execute as @s[scores={music.emissary.queue=16},tag=!music.queuepopped] run function music:emissary/phase/8/continue
execute as @s[scores={music.emissary.queue=0},tag=!music.queuepopped] run function music:emissary/end/start
execute as @s[scores={music.emissary.queue=17},tag=!music.queuepopped] run function music:emissary/end/start
tag @s remove music.queuepopped