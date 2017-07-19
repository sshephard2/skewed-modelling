for /l %%r in (15, 15, 150) do (
	jmeter-n.cmd Replicated-control.jmx -Jathletics=%%r
)